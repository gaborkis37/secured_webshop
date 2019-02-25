package com.secured_webshop.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table( name="users" )
public class User {
		
		@Id
		@GeneratedValue
		private Long Id;

		private String firstName;
		private String lastName;
		
		private String postalCode;
		private String country;
		
		private String city;
		private String address;
		
		@Column( unique=true , nullable=false)
		private String email;
		@Column(nullable=false)
		private String password;
		
		
		private String activation;
		
		private Boolean enabled; 
		
		@ManyToMany(cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
		@JoinTable(
				name = "users_roles",
				joinColumns = {@JoinColumn(name="user_id")},
				inverseJoinColumns= {@JoinColumn(name="role_id")}
				)
		private Set<Role> roles = new HashSet<Role>();
		
		

		public User(){}
		

		public Long getId() {
			return Id;
		}




		public void setId(Long id) {
			Id = id;
		}




		public String getFirstName() {
			return firstName;
		}



		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}



		public String getLastName() {
			return lastName;
		}



		public void setLastName(String lastName) {
			this.lastName = lastName;
		}



		public String getPostalCode() {
			return postalCode;
		}



		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}



		public String getCountry() {
			return country;
		}



		public void setCountry(String country) {
			this.country = country;
		}



		public String getCity() {
			return city;
		}



		public void setCity(String city) {
			this.city = city;
		}



		public String getAddress() {
			return address;
		}



		public void setAddress(String address) {
			this.address = address;
		}



		public String getEmail() {
			return email;
		}



		public void setEmail(String email) {
			this.email = email;
		}



		public String getPassword() {
			return password;
		}



		public void setPassword(String password) {
			this.password = password;
		}




		public Set<Role> getRoles() {
			return roles;
		}




		public void setRoles(Set<Role> roles) {
			this.roles = roles;
		}	
		
		
		
		public String getActivation() {
			return activation;
		}


		public void setActivation(String activation) {
			this.activation = activation;
		}


		public Boolean getEnabled() {
			return enabled;
		}


		public void setEnabled(Boolean enabled) {
			this.enabled = enabled;
		}


		public void addRoles(String roleName) {
			if (this.roles == null || this.roles.isEmpty()) 
				this.roles = new HashSet<>();
			this.roles.add(new Role(roleName));
		}
		
		
		
		
	}
