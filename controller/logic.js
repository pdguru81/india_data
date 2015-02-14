var express = require('express');
var router = express.Router();
var asha =  require('./../model/asha');
var mothers =  require('./../model/mother');



var signup_methods = {};


signup_methods.createMothers = function(name,phone,emergency_contact){
	if (name===null ||typeof name==='undefined' || phone===null || 

    	typeof phone ==="undefined" ||emergency_contact===null || typeof 
    	emergency_contact==="undefined"){

    	 var err= "Name or Phone or emergency_contact cannot be empty for Mothers.";

    	 var status = 400;

    	 res.send({status:status,message:err,success:false});
    }else{
    	    	// check the database if this user already exists
    	mothers.count({phone:phone},

			function(err,count){

				if(count===0){

					//create this user and add to database
					var newAsha = new asha({

						name: name,

						phone: phone,

						password:password,

						hospital: hospital
					});
				
					newAsha.save(function(err,response){
						if(!err){
							var sts = 200;
							var msg = "Successfully created user";
							res.send({status:sts,message:msg, success:true})
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

//creates an asha and adds  them to the database
signup_methods.ashaSignUP =function(name, phone, hospital,password, res){
		
    if (name===null ||typeof name==='undefined' || phone===null || 

    	typeof phone ==="undefined" || password===null || typeof 
    	password==="undefined"|| hospital===null|| typeof hospital==="undefined"){

    	 var err= "Name or Phone or Hospital cannot be empty for Ashas.";

    	 var status = 400;

    	 res.send({status:status,message:err,success:false});

    }else{
    	// check the database if this user already exists
    	asha.count({phone:phone},

			function(err,count){

				if(count===0){

					//create this user and add to database
					var newAsha = new asha({

						name: name,

						phone: phone,

						password:password,

						hospital: hospital
					});
				
					newAsha.save(function(err,response){
						if(!err){
							var sts = 200;
							var msg = "Successfully created user";
							res.send({status:sts,message:msg, success:true})
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