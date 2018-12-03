/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

/**
 *
 * @author Philip
 */
public class User
{
    int id;
    String username;

    public User(int id, String username)
    {
        this.id = id;
        this.username = username;
    }

    public String getName()
    {
        return username;
    }

    public int getID()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return username;
    }
}
