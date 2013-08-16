/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package currencyConverter.view;

import currencyConverter.controller.ConversionFacade;

import currencyConverter.model.IConversion;
import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Conversation;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Inject;


/**
 *
 * @author alberto
 */
@Named(value = "converter")
@ConversationScoped
public class Converter
   implements Serializable
{
   @EJB
   private ConversionFacade convFacade;
   private String homeCurrency;
   private String foreignCurrency;
   private IConversion homeConversion;
   private IConversion foreignConversion;
   private Float money = 0.0f;
   private Float rate = 0.0f;
   private Float convResult = 0.0f;
   private Exception transactionFailure;
   private boolean showRate = false;
   @Inject
   private Conversation conversation;
   private boolean showConversion = false;


   private void startConversation()
   {
      if (conversation.isTransient())
      {
         conversation.begin();
      }
   }


   private void stopConversation()
   {
      if (!conversation.isTransient())
      {
         conversation.end();
      }
   }


   private void handleException(Exception e)
   {
      stopConversation();
      e.printStackTrace(System.err);
      transactionFailure = e;
   }


   /**
    * Set the value of the homeCurrency
    * @param currency 
    */
   public void setHomeCurrency(String currency)
   {
      this.homeCurrency = currency;
   }


   /**
    * 
    */
   public String getHomeCurrency()
   {
      return homeCurrency;
   }


   /**
    * Set the value of the foreignCurrency
    * @param currency 
    */
   public void setForeignCurrency(String currency)
   {
      this.foreignCurrency = currency;
   }


   /**
    *
    */
   public String getForeignCurrency()
   {
      return foreignCurrency;
   }


   /**
    * set the value of money to change
    * @param money 
    */
   public void setMoney(Float money)
   {
      this.money = money;
   }


   /**
    *
    * @return 
    */
   public Float getMoney()
   {
      return money;
   }


   public Float getConvResult()
   {
      return convResult;
   }


   public void setConvResult(Float cr)
   {
      convResult = cr;
   }


   public void findRates()
   {
      try
      {
         startConversation();
         transactionFailure = null;
         homeConversion = convFacade.findConversion(homeCurrency);
         foreignConversion = convFacade.findConversion(foreignCurrency);
         rateCalculator();
         showRate = true;

      }
      catch (Exception e)
      {
         handleException(e);
      }
   }


   /**
    * Returns the latest thrown exception.
    */
   public Exception getException()
   {
      return transactionFailure;
   }


   /**
    * Returns <code>false</code> if the latest transaction failed, otherwise <code>false</code>.
    */
   public boolean getSuccess()
   {
      return transactionFailure == null;
   }


   private void rateCalculator()
   {
      if (homeCurrency.equals("SEK"))
      {
         rate = homeConversion.getRate() * foreignConversion.getRate();
      }
      if (homeConversion.equals(foreignConversion))
      {
         rate = homeConversion.getRate() / foreignConversion.getRate();
      }
      else
      {
         rate = foreignConversion.getRate() / homeConversion.getRate();
      }
   }


   public boolean getRateDone()
   {
      return showRate;
   }


   public Float getRate()
   {
      return rate;
   }


   public void convert()
   {
      findRates();
      
      if (money == null || money.isNaN() || money < 0)
      {
         handleException(new Exception("The money must be a positive float number."));
      }
      else
      {
         convResult = money * rate;
         showConversion = true;
      }
   }


   public boolean getShowConvert()
   {
      return showConversion;
   }


}
