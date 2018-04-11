/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cupcake.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.cupcake.entities.Events;
import edu.cupcake.utils.Connexion;

/**
 *
 * @author El Kamel
 */
public class EventsService {

        Connection cn = Connexion.getInstance().getConnection();


    public void AddEvent(Events e) {

        String requete = "INSERT INTO events(enseigne_id,DateStart,Title,Description,Image,Type,Adress,nbPlaces,nbPart) VALUES (?,?,?,?,?,?,?,?,?)";

        try {

            PreparedStatement st = cn.prepareStatement(requete);
            st.setInt(1, e.getEnseigneId());
            st.setDate(2, e.getDateStart());
            st.setString(3, e.getTitle());
            st.setString(4, e.getDescription());
            st.setString(5, e.getImage());
            st.setString(6, e.getType());
            st.setString(7, e.getAdress());
            st.setInt(8, e.getNbPlaces());
            st.setInt(9, e.getNbPart());

            st.executeUpdate();
            System.out.println("Event ajoutée");

        } catch (SQLException ex) {
            Logger.getLogger(EventsService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void EditEvent(Events e, int id) throws SQLException {

        String req = "UPDATE events SET enseigne_id=?,DateStart=?,Title=?,Description=?,Image=?,Type=?,Adress=?,nbPlaces=?,nbPart=? WHERE id = ?";
        PreparedStatement st = cn.prepareStatement(req);

        st.setInt(1, e.getEnseigneId());
        st.setDate(2, e.getDateStart());
        st.setString(3, e.getTitle());
        st.setString(4, e.getDescription());
        st.setString(5, e.getImage());
        st.setString(6, e.getType());
        st.setString(7, e.getAdress());
        st.setInt(8, e.getNbPlaces());
        st.setInt(9, e.getNbPart());
        st.setInt(10, id);
        st.executeUpdate();

    }

    public void DeleteEvent(int id) throws SQLException {
        String req = "DELETE FROM events WHERE id=?";
        PreparedStatement pre = cn.prepareStatement(req);
        pre.setInt(1, id);
        pre.executeUpdate();

    }

    public Events FindById(int id) throws SQLException {
        Events e = new Events();

        String req = "SELECT * FROM `events` WHERE id = ?";
        PreparedStatement pre = cn.prepareStatement(req);
        pre.setInt(1, id);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {

            e.setId(rs.getInt("id"));
            e.setEnseigneId(rs.getInt("enseigne_id"));
            e.setDateStart(rs.getDate("DateStart"));
            e.setTitle(rs.getString("Title"));
            e.setDescription(rs.getString("Description"));
            e.setImage(rs.getString("Image"));
            e.setType(rs.getString("Type"));
            e.setAdress(rs.getString("Adress"));
            e.setNbPlaces(rs.getInt("nbPlaces"));
            e.setNbPart(rs.getInt("nbPart"));

        }

        return e;
    }

    public List<Events> FindAll() {

        List<Events> listEvents = new ArrayList<>();

        try {

            String req = "SELECT * FROM events";
            PreparedStatement st = cn.prepareStatement(req);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                listEvents.add(new Events(
                rs.getInt("id"),
                rs.getDate("DateStart"),
                rs.getString("Title"),
                rs.getString("Description"),
                rs.getString("Image"),
                rs.getString("Type"),
                rs.getString("Adress"),
                rs.getInt("nbPlaces"),
                rs.getInt("nbPart"),
                rs.getInt("enseigne_id")
                ));

            }

            System.out.println(listEvents.toString());

        } catch (SQLException ex) {
            Logger.getLogger(EventsService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listEvents;
    }
    
        public List<Events> FindByEnseigneID(int id) {

        List<Events> listEvents = new ArrayList<>();

        try {

            String req = "SELECT * FROM events WHERE enseigne_id = ?";
            PreparedStatement pre = cn.prepareStatement(req);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            
            while (rs.next()) {
                listEvents.add(new Events(
                rs.getInt("id"),
                rs.getDate("DateStart"),
                rs.getString("Title"),
                rs.getString("Description"),
                rs.getString("Image"),
                rs.getString("Type"),
                rs.getString("Adress"),
                rs.getInt("nbPlaces"),
                rs.getInt("nbPart"),
                rs.getInt("enseigne_id")
                ));

            }

            System.out.println(listEvents.toString());

        } catch (SQLException ex) {
            Logger.getLogger(EventsService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listEvents;
    }

}
