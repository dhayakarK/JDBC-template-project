package com.assignment.springassignment.controller;

import com.assignment.springassignment.Exception.EmployeeNotFound;
import com.assignment.springassignment.entities.Employee;
import com.assignment.springassignment.entities.EmployeeRepository;
import com.assignment.springassignment.entities.FileStorage;
import com.assignment.springassignment.entities.UploadFileResponse;
import com.assignment.springassignment.service.EmployeeService;
import org.apache.el.stream.Stream;
import org.hibernate.validator.internal.util.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sun.misc.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

@RestController
@RequestMapping(value = "/Employees")
public class EmployeeController {
    public static String upload_dir="C:/Users/Admin/Pictures/springimages";
     @Autowired
     public FileStorage fileStorage;

     @Autowired
    public EmployeeRepository employeeService;
     @RequestMapping(value = "/upload/{id}",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
     public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file)
     {
         File uploadfile=new File("/Users/Admin/Pictures/springimages",file.getOriginalFilename());
         try
         {
             uploadfile.createNewFile();
             FileOutputStream fileOutputStream=new FileOutputStream(uploadfile);
             fileOutputStream.write(file.getBytes());
             fileOutputStream.close();
         } catch (Exception e) {
             e.printStackTrace();
         }
         return new ResponseEntity<Object>("file uploaded successfully ",HttpStatus.OK);
     }
     @RequestMapping(value = "/uploadmultiplefiles/{id}",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
     public ResponseEntity<Object> uploadMultipleFiles(@RequestParam("multiplefiles") MultipartFile[] multipartFiles)
     {
         FileOutputStream fileOutputStream=null;
         for (MultipartFile multipartFile:multipartFiles) {

             try
             {
                 File uploadfile=new File("/Users/Admin/Pictures/springimages",multipartFile.getOriginalFilename());
                 uploadfile.createNewFile();
                 fileOutputStream=new FileOutputStream(uploadfile);
                 fileOutputStream.write(multipartFile.getBytes());
                 fileOutputStream.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         return new ResponseEntity<>("All files uploaded successfully",HttpStatus.OK);
     }
     //Method for downloading a file
    @RequestMapping(value = "downloadfile/{filename:.+}",method = RequestMethod.GET,produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Object> downloadFile(@PathVariable String filename, HttpServletResponse response)
    {
        String filepath="/Users/Admin/Pictures/springimages/"+filename;
        Path path= Paths.get(filepath);
        UrlResource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
       if (resource.exists())
       {
           return ResponseEntity.ok()
                   .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                   .body(resource);

       }
       else
           return new ResponseEntity("filenotfound",HttpStatus.OK);
    }

     @PostMapping(value = "/POST")
     public @ResponseBody Employee addEmployee(@Valid @RequestBody  Employee employee) throws SQLException {
         return employeeService.addemployee(employee);
     }

    @GetMapping(value = "/GET")
    public List<Employee> getAllemployees() throws SQLException {
        return employeeService.getAllemployees();
    }
    @GetMapping(value = "GET/{employeeId}")
    public Employee getEmployee(@PathVariable("employeeId") int employeeId)  {
         Employee emp=employeeService.getemployeeid(employeeId);
         if (emp==null)
             throw new EmployeeNotFound("record not available");
        return emp;
    }
    @PutMapping("PUT/{employeeId}")
    public Employee updatemployee(@PathVariable("employeeId") int employeeId,@RequestBody Employee employee) throws SQLException {
        return employeeService.updateEmployee(employeeId,employee);
    }
    @DeleteMapping(value = "DELETE/{employeeId}")
    public void deleteemployee(@PathVariable("employeeId") int employeeId) throws SQLException {
        employeeService.deleteEmployee(employeeId);
    }
    //method to describing about header
    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestHeader("say-welcome") String language)
    {
        language="am java language";
        return new ResponseEntity<>(language,HttpStatus.OK);
    }
    //a method to multiple a number by 2 and returns the corresponding value in postman
    @GetMapping("/double")
    public ResponseEntity<String> number(@RequestHeader("my-number") int mynumber)
    {
        return new ResponseEntity<String>(String.format("%d*2=%d",mynumber,(mynumber*2)),HttpStatus.OK);
    }
    //we can also use RequestHeader without using the specific name
    @GetMapping("/name")
    public ResponseEntity<String> listAllHeaders(@RequestHeader HttpHeaders headers)
    {
        InetSocketAddress host=headers.getHost();
        String url="http://"+host.getHostName()+":"+host.getPort();
        return new ResponseEntity<String>(String.format("Base url=%s",url),HttpStatus.OK);
    }
    //method to describe Request header may or may not be contain a parameter through "required" parameter
    @GetMapping("/NonrequiredHeader")
    public ResponseEntity<String> evaluateHeader(@RequestHeader(value = "optional-param",required = true) String Optional)
    {
        return new ResponseEntity<String>(String.format("was the optional header present ? %s !",(Optional==null?"No":"Yes")),HttpStatus.OK);
    }
    //method to describe on providing a default value to the RequestHeader
    @GetMapping("/default")
    public ResponseEntity<String> evaluateDefault(@RequestHeader(value = "optional-header",defaultValue = "7000") int optionalheade)
    {
        return new ResponseEntity<String>(String.format("default value =%d",optionalheade),HttpStatus.OK);
    }
}
