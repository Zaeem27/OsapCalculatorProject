package listener;

import javax.servlet.ServletRequestAttributeEvent;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import jdk.nashorn.internal.runtime.Context;

/**
 * Application Lifecycle Listener implementation class MaxPrincipal
 *
 */
@WebListener
public class MaxPrincipal implements HttpSessionAttributeListener {

    /**
     * Default constructor. 
     */
	Double maxPrincipal=0.0;
	
    public MaxPrincipal() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    	if (arg0.getName().equals("principal")) {
    		handleEvent(arg0);
    	}    	
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    	if (arg0.getName().equals("principal")) {
    		handleEvent(arg0);
    	}  
    }
    
    public void handleEvent(HttpSessionBindingEvent event) {
    	Double newMax = Double.parseDouble(event.getSession().getAttribute("principal").toString());;
    	if (newMax > maxPrincipal) {
    		maxPrincipal = newMax;
    	}
    	
    	event.getSession().getServletContext().setAttribute("maxPrincipal", maxPrincipal);
    	//System.out.println(event.getSession().getServletContext().getAttribute("maxPrincipal").toString());
    }
	
}
