var mongoose = require('mongoose');
var express= require('express');


/**
This represents an asha object.
An asha belongs to a  Hospital, and  has a name, a phone and a password_hash 
**/
var ashaSchema = mongoose.Schema({
	name: String,
	phone: String,
	password_hash: String,
	hospital: String
});

var asha = mongoose.model('Asha', ashaSchema);

module.exports = asha;