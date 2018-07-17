/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBconnection;

/**
 *
 * @author Admin
 */
public class student {
    private String ID, Name, email,phone,diachi,avatar;
    private int  gt;

    public student(String ID, String Name, String email, String phone,int gt, String diachi, String avatar) {
        this.ID = ID;
        this.Name = Name;
        this.email = email;
        this.phone = phone;
        this.diachi = diachi;
        this.avatar = avatar;
        this.gt = gt;
    }
    public void student(){
         this.ID = ID;
        this.Name = Name;
        this.email = email;
        this.phone = phone;
        this.diachi = diachi;
        this.avatar = avatar;
        this.gt = gt;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGt() {
        return gt;
    }

    public void setGt(int gt) {
        this.gt = gt;
    }
    
}
