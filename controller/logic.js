var express = require('express');
var router = express.Router();
var asha =  require('./../model/asha');


var signup_methods = {};



//checks if a user with this phone number already exists
var userExists = function(phonenumber,res,next){

	asha.count(

		{phone:phonenumber},

		function(err,count){

			if(count==0){
				//proceed
				next();

			}else{

				var err = " A user with this phone number already exists";

				var status = 400;
				// ask the user to sign up
				res.send({status:status, message:err, success:"false"})
			}
		}
	);
}


//creates an asha and adds  them to the database
signup_methods.ashaSignUP =function(name, phone, hospital,res){
		
    if (name===null ||typeof name==='undefined' || phone===null || 

    	typeof phone ==="undefined" || hospital===null|| typeof hospital==="undefined"){

    	 var err= "Name or Phone or Hospital cannot be empty for users.";

    	 var status = 400;

    	 res.send({status:status,message:err,success:false});

    }else{
    	// check the database if this user already exists
    	asha.count(

		{phone:phone},

		function(err,count){

			if(count==0){

				//create this user and add to database
				var newAsha = new asha({

					name: name,

					phone: phone,

					hospital: hospital
				});
			
				newAsha.save(function(err,res){
					if(!err){
						var status = 200;
						var message = "Successfully created user";
						res.send({status:status,message:message, success:true})
					}
				});
			}else{

				var err = " A user with this phone number already exists";

				var status = 400;
				// ask the user to sign up
				res.send({status:status, message:err, success:"false"})
			}
		}
	);

    }

}



module.exports =  signup_methods;