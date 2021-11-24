/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agency.payload;

/**
 *
 * @author AKPONA Christian
 */
public class ResetPassword {
    
    private String newPassword;
    

    public ResetPassword() {
    }

    public ResetPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
 
}
