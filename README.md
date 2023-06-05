**Store Management Application**

This represents an API for product management. The operations that are offered by this API are the following:

1.Add Product

2.Find product by name

3.Change product price

4.Delete a product


Application has also setup basic authorisation in spring security, at start-up it will insert in database 2 test users, each of them with a
different authority:
1.Admin user with following credentials -> username:testUserAdmin, password:test@1234, authority: ADMIN
2.Client user with the following credentials -> username: testUserClient, password:test@1234, authority: USER

Security level was designed with a JPAUserService, there will be created a User table at start-up where it will insert 
those 2 default users(I need to mention that I didn`t add separate endpoints for user operations(create,add,delete)
but this is very simple if is needed.


Based on those authorities the operations listed above can be performed as following: finding a product by name can be performed by a USER or an ADMIN,
any other operation can be performed only by an ADMIN user.

For logging in the application there was implemented an Aspect defined in logging package. Doing to the fact that 
application has only one service the aspect acts only for this specific class.

Error handling was designed also via AOP, there is defined an ControllerAdvice in the application in exception_handling 
package.

Request object are also validated via "hibernate-validator", so for that I designed a dto object that holds
those validations in front of what is retrieved in service. Those are also treated in ControllerAdvice

There were also unit tests written for service and for controller (junit + mock mvc). Also the scenarios are simple,
keeping in mind that the operations in application are very simple.