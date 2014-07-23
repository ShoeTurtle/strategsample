package com.shoeturtle.eTree;

import java.util.*;

/**
 * User: Binay
 * Date: 22/07/14
 * Time: 8:34 PM
 */
public class Employee {
    private String id;
    private String managerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    /**
     * EmployeeNode Class to represent the Employee Hierarchy Structure
     */
    public static class EmployeeNode {
        private String id;
        private List<EmployeeNode> directReports;
        public static Deque<EmployeeNode> employeeNodesStack = new LinkedList<EmployeeNode>();

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setDirectReports(List<EmployeeNode> empNode) {
            this.directReports = empNode;
        }

        public List<EmployeeNode> getDirectReports() {
            return directReports;
        }

        /**
         * Traversing the Employee eTree
         * @param employeeNode
         */
        public static void traverseEmployeeTree(EmployeeNode employeeNode) {
            if (employeeNode.directReports != null) {
                System.out.println("Employee ID: " + employeeNode.getId());
                System.out.print("Direct Reports: [ ");
                for (EmployeeNode empNode : employeeNode.getDirectReports()) {
                    System.out.print(empNode.getId() + " ");
                    employeeNodesStack.add(empNode);
                }
                System.out.println("]");
            }

            if (employeeNodesStack.isEmpty()) return;

            traverseEmployeeTree(employeeNodesStack.pop());
        }
    }

    public static class Tree {

        public EmployeeNode rootNode = new EmployeeNode();
        public static Deque<Employee> employeeStack = new LinkedList<Employee>();
        public static Deque<EmployeeNode> employeeNodesStack = new LinkedList<EmployeeNode>();

        /**
         * 1. Map DirectReports to their parent id.
         * 2. Store it in a HashMap
         * 3. Recursively traverse the HashMap to build the EmployeeNode structure
         *
         * @param employees
         * @return
         */
        public EmployeeNode buildTree(List<Employee> employees) {
            Iterator<Employee> it = employees.iterator();
            Map<String, List<Employee>> mapDirectReports = new HashMap<String, List<Employee>>();
            Employee rootEmployee = null;

            while (it.hasNext()) {
                Employee employee = it.next();
                if (employee.getManagerId() == null) rootEmployee = employee;

                if (mapDirectReports.containsKey(employee.getManagerId())) {
                    mapDirectReports.get(employee.getManagerId()).add(employee);
                } else {
                    List<Employee> tmp = new ArrayList<Employee>();
                    tmp.add(employee);
                    mapDirectReports.put(employee.getManagerId(), tmp);
                }
            }

            createHierarchy(mapDirectReports, rootEmployee.getId(), rootNode);

            return rootNode;
        }

        /**
         * 1. Recursively Traverse the hierarchyMap
         * 2. Create EmployeeNode objects for the parent id
         * 3. Map the List to populate the directReporting EmployeeNode objects
         * 4. Use stack to keep track of the level depth
         * 5. Repeat until the stack is not empty
         *
         * @param hierarchyMap
         * @param managerID
         * @param employeeNode
         */
        public void createHierarchy(Map<String, List<Employee>> hierarchyMap, String managerID, EmployeeNode employeeNode) {

            if (hierarchyMap.containsKey(managerID)) {
                List<Employee> directReportings = hierarchyMap.get(managerID);

                employeeNode.setId(managerID);
                employeeNode.setDirectReports(new ArrayList<EmployeeNode>());

                int count = 0;
                for (Employee employee : directReportings) {
                    employeeNode.directReports.add(new EmployeeNode());

                    employeeStack.add(employee);
                    employeeNodesStack.add(employeeNode.directReports.get(count++));
                }

            } else {
                employeeNode.setId(managerID);
                employeeNode.setDirectReports(null);
            }

            if (employeeStack.isEmpty()) return;

            createHierarchy(hierarchyMap, employeeStack.pop().getId(), employeeNodesStack.pop());
        }
    }
}
