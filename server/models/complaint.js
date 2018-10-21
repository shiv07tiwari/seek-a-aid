var mongoose = require("mongoose");

var complaintSchema = new mongoose.Schema({
  email             : String,
  content           : String,
  tag               : String,
  status            : [String],
  statusdescription : [String],
  alotedMember1 : String,
  alotedMember2 : String,
  like        : String,
  dislike       : String,
     rating        : String
});


module.exports = mongoose.model("Complaint", complaintSchema);