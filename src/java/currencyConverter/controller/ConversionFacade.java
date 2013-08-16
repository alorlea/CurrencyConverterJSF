/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package currencyConverter.controller;

import currencyConverter.model.Conversion;
import currencyConverter.model.IConversion;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alberto
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
@LocalBean
public class ConversionFacade {
    @PersistenceContext(unitName = "currencyConverterPU")
    private EntityManager em;
    
    public IConversion findConversion(String currency){
        IConversion found = em.find(Conversion.class, currency);
        if(found==null){
            throw new EntityNotFoundException("No conversion currency: " + currency);
        }
        return found;
    }
}
