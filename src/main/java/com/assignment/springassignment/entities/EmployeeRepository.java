package com.assignment.springassignment.entities;

import com.assignment.springassignment.DatabaseConnection;
import com.assignment.springassignment.Exception.EmployeeNotFound;
import com.assignment.springassignment.Exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Path;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
@Component
public class EmployeeRepository {
    private final Path finalStorageLocation=null;
    public static int employeeId;
@Autowired
    JdbcTemplate jdbcTemplate;
public static Connection connection= DatabaseConnection.getConnection();
public Employee addemployee(Employee employee) throws SQLException {
    String query="select *from employee1 where firstname=? or lastname=? or email=? or phone=?";
       PreparedStatement ps1=connection.prepareStatement(query);
       ps1.setString(1,employee.getFirstname());
       ps1.setString(2,employee.getLastname());
       ps1.setString(3,employee.getEmail());
       ps1.setLong(4,employee.getPhone());
       ResultSet set=ps1.executeQuery();
       if (set.next())
       {
           throw new EmployeeNotFound("record already Exist");
       }
       else {

           employeeId=employeeId+1;
           employee.setId(employeeId);
           jdbcTemplate.update("insert into  employee1(firstname, lastname, email, phone) values (?,?,?,?)",new Object[]{employee.getFirstname(),employee.getLastname(),employee.getEmail(),employee.getPhone()});

       }
   return employee;
}
    public List<Employee> getAllemployees() throws SQLException {
        List<Employee> list = jdbcTemplate.query("select *from employee1", ((resultSet, rownumber) -> new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getLong(5))));
        return list;
    }
    public Employee getemployeeid(int employeeId) {
        Employee employee = null;
        String query = "select *from employee1 where id=?";
        employee = jdbcTemplate.queryForObject(query, new Object[]{employeeId}, new BeanPropertyRowMapper<>(Employee.class));

        return employee;
    }
    public Employee updateEmployee(int employeeId,Employee employee) throws SQLException {
        String query1="select *from employee1 where id=?";
        PreparedStatement ps=connection.prepareStatement(query1);
        ps.setInt(1,employeeId);
        ResultSet resultSet=ps.executeQuery();
        if (resultSet.next())
        {
            String query = "update employee1 set firstname=?,lastname=?,email=?,phone=? where id=?";
            employee.setId(employeeId);
            jdbcTemplate.update(query, new Object[]{
                    employee.getFirstname(), employee.getLastname(), employee.getEmail(), employee.getPhone(), Integer.valueOf(employee.getId())
            });
        }
        else {
            throw new EmployeeNotFound("Invalid Id to update employee details");
        }
        return employee;
    }
    public int deleteEmployee(int employeeId)
    {
        String query="delete from employee1 where id=?";
        int size=jdbcTemplate.update(query,employeeId);
        if(size==0)
        {
            throw new EmployeeNotFound("record not found to delete "+employeeId);
        }
        return size;
    }

}
