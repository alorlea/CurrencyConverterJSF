/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package currencyConverter.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author alberto
 */
@Entity
public class Conversion implements Serializable , IConversion {
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id private String currency;
    private float rate;
    
    
    public Conversion(){
        
    }
    
    public Conversion(String currency, float rate){
        this.rate=rate;
        this.currency=currency;
        
    }
    
   
    @Override
    public float getRate() {
        return rate;
    }

    @Override
    public String getCurrency() {
        return currency;
    }
    
    @Override
    public int hashCode() {
        return currency.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Conversion)) {
            return false;
        }
        Conversion other = (Conversion) object;
        if (!((currency.equals(other.getCurrency()))&&rate==other.getRate())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "currencyConverter.model.Conversion[ Currency=" + currency + " rate:"+rate+ " ]";
    }

}
