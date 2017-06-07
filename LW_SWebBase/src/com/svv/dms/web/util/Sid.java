package com.svv.dms.web.util;

public class Sid  implements java.io.Serializable{

    private static final long serialVersionUID = -7431042960322190854L;
    private String locationId;
      public Sid() {          
          String lid = String.valueOf(Math.random());
          locationId = lid.substring(2);
      }      
      
      public Sid(String id){
          this.locationId = id;
      }
     
      public String toString() {
          return locationId;
      } 
          
}

