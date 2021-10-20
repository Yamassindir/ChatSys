/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

/**
 *
 * @author Fabio
 */
public class Hello extends Message {
    private String username;
    private boolean responseRequired;

    public Hello(long timestamp, String username, boolean responseRequired){
        super(timestamp);
        this.username = username;
        this.responseRequired = responseRequired;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isResponseRequired() {
        return responseRequired;
    }

    public void setResponseRequired(boolean responseRequired) {
        this.responseRequired = responseRequired;
    }
    
}
