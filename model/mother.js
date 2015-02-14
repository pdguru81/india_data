var mongoose = require('mongoose');
var express= require('express');


/**
This represents an mother object.
A mother has a name, a phone and an emergency_contact
**/
var motherSchema = mongoose.Schema({
	name: String,
	phone: String,
	emergency_contact: String,

});

var mother = mongoose.model('mother',motherSchema);

module.exports = mother;