package com.svv.dms.web;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class UGID {
    private static int A = 0;
    private static int B = 0;
    private static Random r = new Random();
    private static SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssSSS");
    

	public static String getTableIndex(String auid){
		return auid.substring(17,19);
	}
	public static String getServerIndex(String auid){
		return auid.substring(15,17);
	}
	
	private static AUIDServer _AUIDServer = null;
    
	public UGID(final int capacity){
		_AUIDServer = new AUIDServer(capacity);
		_AUIDServer.run();
		
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            public void run() {
    	        while(true){
                    try {
                    	_AUIDServer.flag = _AUIDServer.queue.size()<=capacity/2;
                		//System.out.println("[AUIDSever] 内存： "+_AUIDServer.queue.size()+" 个  flag="+(_AUIDServer.flag?"true":"false"));
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
    	        }
            }
        });
	}

	public String get(){
		return _AUIDServer.get();
	}
    
	public static void main(String[] args){
        int  flag = 1;///////////////////////////////////////
        if(flag==0){
			UGID AUID = new UGID(1);
			for(int i=0;i<10;i++){
				String b = AUID.get();
				System.out.println(b +"   ,"+b.length());
			}
        }

		if(flag==1){
			Map<String, String> ss = new HashMap<String, String>();
			UGID AUID = new UGID(1000000);
			String b = AUID.get();
			System.out.println(b +"   ,"+b.length());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	
			long s = new Date().getTime();
			System.out.println("时间： "+(new Date().getTime()-s) +" ms  共产生： "+ss.size()+" 个    "+ new Date());
			for(int i=0;i<600000;i++){
				String a = AUID.get();
				ss.put(a, a);
				//System.out.println(AUID.get());
			}
			System.out.println("时间： "+(new Date().getTime()-s) +" ms  共产生： "+ss.size()+" 个    "+ new Date());
			for(int i=0;i<600000;i++){
				String a = AUID.get();
				ss.put(a, a);
				//System.out.println(AUID.get());
			}
			System.out.println("时间： "+(new Date().getTime()-s) +" ms  共产生： "+ss.size()+" 个    "+ new Date());
			for(int i=0;i<600000;i++){
				String a = AUID.get();
				ss.put(a, a);
				//System.out.println(AUID.get());
			}
			System.out.println("时间： "+(new Date().getTime()-s) +" ms  共产生： "+ss.size()+" 个    "+ new Date());
			System.out.println("共产生： "+ss.size()+" 个");
			System.out.println("A： "+A+"   B:"+B);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}

	
	class AUIDServer {
	    private BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	    private boolean flag = false;
	    //存储空间
	    protected int capacity = 10000;
	    
	    public AUIDServer(int capacity){
	    	this.capacity = capacity;
	    }
	    
	    public String get(){
	    	if(queue.isEmpty()){
	    		A++;
	    		createOne();
	    	}
	    	return queue.poll();
	    }
	    
	    public void run(){
	    	flag = queue.size()<=capacity/2;
	        Executors.newSingleThreadExecutor().submit(new Runnable() {
	            public void run() {
	            	int cmax = 200000;
	    	        while(true){
    	        		//System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++ falg=" + (flag?"true":"false"));
	    	        	if(flag){
	                    	int addmax = capacity-queue.size();
	                    	int c = (int)Math.ceil(addmax*1.0/cmax);
	    	        		//System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++ c=" + c);
	                        try {
	                        	for(int i=0;i<c;i++){
	                            	createMulty(Math.min(addmax, (i+1)*cmax)- i*cmax);
	                                Thread.sleep(200);
	                        	}
	                        } catch (Exception e) {
	                            e.printStackTrace();
	                        }
	                		System.out.println("[AUIDSever] 内存： "+_AUIDServer.queue.size()+" 个  flag="+(_AUIDServer.flag?"true":"false"));
	    	        	}
                        try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    	        }
	            }
	        });
	        Executors.newSingleThreadExecutor().submit(new Runnable() {
	            public void run() {
	    	        while(true){
	    	        	flag = queue.size()<capacity;
	    	        }
	            }
	        });
	    }
	    private void createMulty(int count){
	    	
        	for(int i=0; i<count; i++){
        		createOne();
        		B++;
        	}
	    }
		
		private void createOne(){
			try {
				queue.put(createUGID());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static String createUGID(){
		StringBuilder auid = new StringBuilder();
		auid.append("01");//String ServerIndex = "01"; // 02 03
		auid.append("01");//String DataTableIndex = "01"; // 02 03 04....
        auid.append(((1000000+r.nextInt(999999))+"").substring(1)+((100000+r.nextInt(99999))+"").substring(1));
        return format.format(new Date()).concat(auid.toString());
	}
}
