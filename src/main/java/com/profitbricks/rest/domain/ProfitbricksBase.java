/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.profitbricks.rest.domain;

/**
 *
 * @author jasmin.gacic
 */
public class ProfitbricksBase {

   public ProfitbricksBase() {

   }
   
   public String id;
   public String type;
   public String href;
   public Metadata metadata = new Metadata();
}