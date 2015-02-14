var mongoose = require('mongoose');
var express= require('express');


/**
This represents an mother object.
A mother has a name, a phone and an emergency_contact
**/
var motherSchema = mongoose.Schema({
	asha: {type : mongoose.Schema.Types.ObjectId, ref: 'Asha'},
	name: String,
	phone: String,
	emergency_contact_phone: String
});

var mother = mongoose.model('Mother', motherSchema);

module.exports = mother;