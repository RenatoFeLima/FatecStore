/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.fatec.fatecstore.DAO;

import java.sql.SQLException;
import java.util.Collection;

/**
 *
 * @author Renato Ferreira
 */
public interface DAO <MODEL> {
 
    public boolean insere(MODEL obj) 
            throws SQLException;
    public boolean remove(MODEL obj) 
            throws SQLException;
    public boolean altera(MODEL obj) 
            throws SQLException;
    public MODEL buscaID(MODEL obj) 
            throws SQLException;
    public Collection<MODEL> lista(String criterio) 
            throws SQLException;    
    
}