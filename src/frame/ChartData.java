/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset; 
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author User
 */
public final class ChartData extends javax.swing.JFrame {
    
    static Connection conn;
    PreparedStatement st;
    ResultSet rs;
    
    // SET Frame Title to Chart Name
    public ChartData(String applicationTitle) {
        super(applicationTitle);
    }
    
    // Get Bar Chart Dataset from MySql Table 
    public static CategoryDataset getBarDataset(String query) {
        JDBCCategoryDataset dataset = null;
        try {
            conn = DBConnection.getConnection();
            dataset = new JDBCCategoryDataset(conn, query);
            dataset.executeQuery(query);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChartData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataset;
    }
    
    // Create Bar Chart for Project Status vs Total Cost
    public JPanel statusVStcBarChart(String title, String xAxis, String yAxis) {
        String query = "SELECT projectStatus, totalCost FROM project";
        // JDBCCategoryDataset dataset = getBarDataset(query);
        CategoryDataset dataset = getBarDataset(query);
        JFreeChart chart = ChartFactory.createBarChart(title, xAxis, yAxis, dataset, PlotOrientation.VERTICAL, true, true, false);   
        ChartPanel cp = new ChartPanel(chart);
        cp.setPreferredSize(new java.awt.Dimension(600, 600));
        cp.setDomainZoomable(true);
        setContentPane(cp);
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
	setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        return cp;
    }
    
    // Create Bar Chart for ecMaxContributions vs Project Status
    public JPanel statusVSecBarChart(String title, String xAxis, String yAxis) {
        String query = "SELECT proName, ecMaxContrib FROM project WHERE projectStatus=SIGNED";
        // JDBCCategoryDataset dataset = getBarDataset(query);
        CategoryDataset dataset = getBarDataset(query);
        JFreeChart chart = ChartFactory.createBarChart(title, xAxis, yAxis, dataset, PlotOrientation.VERTICAL, true, true, false);   
        ChartPanel cp = new ChartPanel(chart);
        cp.setPreferredSize(new java.awt.Dimension(600, 600));
        cp.setDomainZoomable(true);
        setContentPane(cp);
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
	setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        return cp;
    }
    
    // Create Bar Chart for Funding Scheme vs Total Cost
    public JPanel schemeVStcBarChart(String title, String xAxis, String yAxis) {
        String query = "SELECT fundingScheme, totalCost FROM project";
        CategoryDataset dataset = getBarDataset(query);
        JFreeChart chart = ChartFactory.createBarChart(title, xAxis, yAxis, dataset, PlotOrientation.HORIZONTAL, true, true, false);   
        ChartPanel cp = new ChartPanel(chart);
        cp.setPreferredSize(new java.awt.Dimension(600, 750));
        cp.setDomainZoomable(true);
        setContentPane(cp);
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
	setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        return cp;
    }
    
    // Create Bar Chart for Total Cost vs ecMaxContrib
    public JPanel tcVSecBarChart(String title, String xAxis, String yAxis) {
        String query = "SELECT totalCost, ecMaxContrib FROM project";
        CategoryDataset dataset = getBarDataset(query);
        JFreeChart chart = ChartFactory.createBarChart(title, xAxis, yAxis, dataset, PlotOrientation.VERTICAL, true, true, false);   
        ChartPanel cp = new ChartPanel(chart);
        cp.setPreferredSize(new java.awt.Dimension(600, 600));
        setContentPane(cp);
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
	setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        return cp;
    }
    
    // Other Team Members can add their own code for their own charts here using Different JDBC Datasets defined above
    
    
}
