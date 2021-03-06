var express = require('express');
var router = express.Router();
var asha =  require('./../model/asha');
var mothers = require("./../model/mother");


var signup_methods = {};


//check that args are of none-zero length
var checklength = function(){
	for (var i = 0; i < arguments.length; i++) {
    	if(arguments[i].length===0){
    		return false;
    	}
  }

  	return true;
}

signup_methods.createMothers = function(asha_phone,name,phone,emergency_contact_phone,res){
	console.log("This is the asa phone: "+asha_phone);
	if (name===null ||typeof name==='undefined' || phone===null || 

    	typeof phone ==="undefined" ||emergency_contact_phone===null || typeof 
    	emergency_contact_phone==="undefined"){

    	 var err= "Name or Phone or emergency_contact_phone cannot be empty for Mothers.";

    	 var status = 400;

    	 res.send({status:status,message:err,success:false});
    }else{
    	// check the database if this user already exists
    	mothers.count({phone:phone},

			function(err,count){

				if(count===0){

					asha.findOne({phone: asha_phone}, function(err, asha){

						if (asha){

							//create this user and add to database
							var mother = new mothers({

								asha: asha._id,

								name: name,

								phone: phone,

								emergency_contact_phone:emergency_contact_phone

							});
						
							mother.save(function(err,response){
								if(!err){
									var sts = 200;
									var msg = "Successfully created a Mother Object";
									res.send({status:sts,message:msg, success:true})
								}
							});

						} else {
							res.status(500).json({success: false, error: "Database error"});
						}			
					});

				}else{

					var err = " A mother with this phone number already exists";

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
	
    if (name === null || typeof name === 'undefined' || phone === null || 

    	typeof phone === "undefined" || password === null || typeof 
    	password === "undefined" || hospital === null || typeof hospital === "undefined"){

    	 var err= "Name or Phone or Hospital cannot be empty for Ashas.";

    	 var status = 400;

    	 res.send({status:status,message:err,success:false});

    } else {
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

					var err = " An Asha with this phone number already exists";

					var status = 400;
					// ask the user to sign up
					res.send({status:status, message:err, success:"false"})
				}
			}
	);

    }

}



module.exports =  signup_methods;