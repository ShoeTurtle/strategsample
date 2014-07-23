package com.shoeturtle.test;

import com.shoeturtle.eTree.Employee;
import com.shoeturtle.ttl.TTL;
import com.shoeturtle.ttl.TTLCache;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Binay
 * Date: 22/07/14
 * Time: 8:39 PM
 */
public class TestMe {

    @Test
    public void testTTLCache() throws Exception {
        TTLCache cache = new TTL();

        cache.put("key1", "value1", 5);
        cache.put("key2", "value2", 10);

        assert cache.get("key1") == "value1"; // should print value1
        assert cache.get("key2") == "value2"; // should print value2

        Thread.sleep(1000*6);

        assert cache.get("key1") == null; // should print NULL
        assert cache.get("key2") == "value2"; // should print value2

        Thread.sleep(1000*6);

        assert cache.get("key1") == null; // should print NULL
        assert cache.get("key2") == null; // should print NULL

    }


    @Test
    public void testEmployeeTree() throws Exception {

        Employee employee_1 = new Employee();
        employee_1.setId("1");
        employee_1.setManagerId(null);

        Employee employee_2 = new Employee();
        employee_2.setId("2");
        employee_2.setManagerId("1");

        Employee employee_3 = new Employee();
        employee_3.setId("3");
        employee_3.setManagerId("1");

        Employee employee_4 = new Employee();
        employee_4.setId("4");
        employee_4.setManagerId("2");

        Employee employee_5 = new Employee();
        employee_5.setId("5");
        employee_5.setManagerId("2");

        Employee employee_6 = new Employee();
        employee_6.setId("6");
        employee_6.setManagerId("1");

        Employee employee_7 = new Employee();
        employee_7.setId("7");
        employee_7.setManagerId("3");

        Employee employee_8 = new Employee();
        employee_8.setId("8");
        employee_8.setManagerId("3");

        Employee employee_9 = new Employee();
        employee_9.setId("9");
        employee_9.setManagerId("8");

        List<Employee> Organization = new ArrayList<Employee>();

        Organization.add(employee_1);
        Organization.add(employee_2);
        Organization.add(employee_3);
        Organization.add(employee_4);
        Organization.add(employee_5);
        Organization.add(employee_6);
        Organization.add(employee_7);
        Organization.add(employee_8);
        Organization.add(employee_9);

        Employee.Tree employeeTree = new Employee.Tree();
        Employee.EmployeeNode topNode = employeeTree.buildTree(Organization);

        System.out.println("\n\nROOT NODE ID -- " + topNode.getId() + "\n");
        Employee.EmployeeNode.traverseEmployeeTree(topNode);
    }

}
