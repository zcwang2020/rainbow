package com.hmall.service.impl;

import org.junit.jupiter.api.Test;

import java.util.List;

public class Jdk17Test {

    @Test
    void jdk() {
        System.out.println(jdk17());
    }

    public static String jdk17() {
        return """ 
                {
                  "id": 12345,
                  "name": "John Doe",
                  "age": 30,
                  "is_student": false,
                  "address": {
                    "street": "1234 Main St",
                    "city": "Anytown",
                    "state": "CA",
                    "zip": "12345"
                  },
                  "phoneNumbers": [
                    {
                      "type": "home",
                      "number": "555-1234"
                    },
                    {
                      "type": "office",
                      "number": "555-5678"
                    }
                  ],
                  "hobbies": [
                    "reading",
                    "swimming",
                    "coding"
                  ],
                  "education": [
                    {
                      "degree": "BSc",
                      "field": "Computer Science",
                      "year": 2015,
                      "school": "University of California"
                    },
                    {
                      "degree": "MSc",
                      "field": "Software Engineering",
                      "year": 2017,
                      "school": "Stanford University"
                    }
                  ],
                  "skills": {
                    "programming_languages": [
                      "Python",
                      "Java",
                      "C++"
                    ],
                    "frameworks": [
                      "React",
                      "Django",
                      "Angular"
                    ],
                    "tools": [
                      "Git",
                      "Docker",
                      "Kubernetes"
                    ]
                  },
                  "employment": [
                    {
                      "title": "Software Engineer",
                      "company": "TechCorp",
                      "start_date": "2017-06-01",
                      "end_date": "2020-08-15",
                      "description": "Developed and maintained software applications."
                    },
                    {
                      "title": "Senior Software Engineer",
                      "company": "Innovatech",
                      "start_date": "2020-09-01",
                      "end_date": "2023-12-31",
                      "description": "Led a team of engineers to develop innovative software solutions."
                    }
                  ],
                  "projects": [
                    {
                      "name": "Project Alpha",
                      "description": "A cutting-edge AI project.",
                      "start_date": "2021-01-01",
                      "end_date": "2022-06-30",
                      "status": "Completed"
                    },
                    {
                      "name": "Project Beta",
                      "description": "A data analytics platform.",
                      "start_date": "2022-07-01",
                      "end_date": "2023-12-31",
                      "status": "Ongoing"
                    }
                  ],
                  "languages": [
                    {
                      "language": "English",
                      "fluency": "Native"
                    },
                    {
                      "language": "Spanish",
                      "fluency": "Intermediate"
                    }
                  ],
                  "references": [
                    {
                      "name": "Jane Smith",
                      "title": "Manager",
                      "company": "TechCorp",
                      "email": "jane.smith@techcorp.com",
                      "phone": "555-8765"
                    },
                    {
                      "name": "Bob Johnson",
                      "title": "Director",
                      "company": "Innovatech",
                      "email": "bob.johnson@inn ovatech.com",
                      "phone": "555-4321"
                    }
                  ]
                }
               """;
    }

    public static void main(String[] args) {
        try {
            // 简单的空指针
            String str = null;
            str.length();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // 复杂一点的空指针
            var arr = List.of(null);
            String str = (String) arr.get(0);
            str.length();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
