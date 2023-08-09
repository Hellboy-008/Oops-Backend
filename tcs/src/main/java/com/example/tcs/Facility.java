package com.example.tcs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "facilities")
//@Data
@AllArgsConstructor
//@NoArgsConstructor
public class Facility extends Member{

}
