package com.assignment.springassignment.service;

import com.assignment.springassignment.DatabaseConnection;
import com.assignment.springassignment.Exception.EmployeeDelNotFound;
import com.assignment.springassignment.Exception.EmployeeNotFound;
import com.assignment.springassignment.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.Entity;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
@Component
public class EmployeeService {
    public static Connection connection= DatabaseConnection.getConnection();
    public  static int employeeId;
    public Employee addemployee( Employee employee) throws SQLException {

           String query1="select *from employee1 where firstname=? or lastname=? or email=? or phone=? ";
           PreparedStatement ps=connection.prepareStatement(query1);
           ps.setString(1,employee.getFirstname());
           ps.setString(2,employee.getLastname());
           ps.setString(3,employee.getEmail());
           ps.setLong(4,employee.getPhone());
           //ps.setString(3,employee.getEmail());
           ResultSet rs=ps.executeQuery();
           if (rs.next())
           {
               throw new EmployeeNotFound("Record already exist");
           }
            else {
                employeeId=employeeId+1;
                employee.setId(employeeId);
               String query = "insert into employee1(firstname, lastname, email, phone) values (?,?,?,?)";
               PreparedStatement preparedStatement = connection.prepareStatement(query);
               preparedStatement.setString(1, employee.getFirstname());
               preparedStatement.setString(2, employee.getLastname());
               preparedStatement.setString(3, employee.getEmail());
               preparedStatement.setLong(4, employee.getPhone());
               preparedStatement.execute();
           }
            return employee;
    }
    public List<Employee> getEmployeeList() throws SQLException {
         ArrayList<Employee> arrylist=new ArrayList<>();
         String query="select *from employee1";
            PreparedStatement ps=connection.prepareStatement(query);
            ResultSet resultSet=ps.executeQuery();
            while (resultSet.next())
            {
                Employee employee=new Employee(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getLong(5));
                arrylist.add(employee);
            }
            return arrylist;
    }
    public Employee getemployeeid(int employeeId)
    {
       Employee emp=null;
        try {

            String query="select *from employee1 where id=?";
            PreparedStatement ps=connection.prepareStatement(query);
            ps.setInt(1,employeeId);
            ResultSet resultSet=ps.executeQuery();
            while (resultSet.next())
            {
                  emp=new Employee(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getLong(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emp;
    }
    public Employee updateEmployee(int employeeId,Employee employee) throws SQLException {
        int employeeid1;
        String query1="select *from employee1 where id=?";
        PreparedStatement ps1=connection.prepareStatement(query1);
        ps1.setInt(1,employeeId);
        ResultSet resultSet=ps1.executeQuery();
        if (resultSet.next()) {
            try {
                String query = "update Employee1 set firstname=?,lastname=?,email=?,phone=? where id=?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, employee.getFirstname());
                ps.setString(2, employee.getLastname());
                ps.setString(3, employee.getEmail());
                ps.setLong(4, employee.getPhone());
                ps.setInt(5, employeeId);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            throw new EmployeeNotFound("Invalid Id to update the details");
        }
        return  employee;
    }
    public void deleteEmployee(int employeeId) throws SQLException {
        String query1="select *from employee1 where id=?";
        PreparedStatement ps1=connection.prepareStatement(query1);
        ps1.setInt(1,employeeId);
        ResultSet resultSet=ps1.executeQuery();
        if (resultSet.next()) {
            try {

                String query = "delete from employee1 where id=?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, employeeId);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            throw new EmployeeDelNotFound("Invalid Record Id for deletion");
        }
    }
}
