const express        = require ("express");
const app            = express();
const bodyParser     = require("body-parser");
const mongoose       = require("mongoose");
var Complaint        = require("./models/complaint");
// var Complaintdesc    = require("./models/complaintdesc");
app.use(express.static("public"));

//setup body-parser
app.use(bodyParser.urlencoded({extended : true}));


// connect to mongodb database on mlab
mongoose.connect("mongodb://admin:swastik0310@ds237373.mlab.com:37373/prototype", function(){
    console.log("connected to mongodb");
});


var userSchema = new mongoose.Schema({
  name         : String,
  email        : String,
  type         : String,
  credits      : String
});

// var complaintSchema = new mongoose.Schema({
//   email             : String,
//   content           : String,
//   tag               : String
// });

var User         = mongoose.model("User", userSchema);
// var Complaint         = mongoose.model("Complaint", complaintSchema);

// Global variable
var gymkhana = [
                    "iit2017048@iiita.ac.in",
                    "iit2017001@iiita.ac.in", 
                    "iit2017119@iiita.ac.in",
                    "iit2016009@iiita.ac.in",
                    "iit2016095@iiita.ac.in",
                    "iit2016507@iiita.ac.in",
                    "iit2015049@iiita.ac.in",
                    "iit2015057@iiita.ac.in",
                    "ecm2017004@iiita.ac.in",
                    "ihm2017009@iiita.ac.in",
                    "ihm2016001@iiita.ac.in",
                    "ecm2015004@iiita.ac.in",
                    "ibm2014008@iiita.ac.in",
                    "icm2014003@iiita.ac.in",
                    "iec2017075@iiita.ac.in",
                    "iec2017052@iiita.ac.in",
                    "iec2016011@iiita.ac.in",
                    "iec2016016@iiita.ac.in",
                    "iec2015501@iiita.ac.in",
                    "rit2015021@iiita.ac.in",
                    "rit2015078@iiita.ac.in",
                    "lit2017031@iiita.ac.in",
                    "lit2016030@iiita.ac.in",
                    "iro2017006@iiita.ac.in",
                    "ihc2017001@iiita.ac.in",
                    "imb2017002@iiita.ac.in",
                    "pwc2015003@iiita.ac.in",
                    "pmi2015003@iiita.ac.in",
                    "pwc2015004@iiita.ac.in"
                ];
                
var gymkhanaMembers = [
                        "iit2017048@iiita.ac.in",
                        "iit2017001@iiita.ac.in", 
                        "iit2017119@iiita.ac.in",
                        "iit2015057@iiita.ac.in",
                        "ecm2017004@iiita.ac.in",
                        "ihm2017009@iiita.ac.in",
                        "ecm2015004@iiita.ac.in",
                        "ibm2014008@iiita.ac.in",
                        "icm2014003@iiita.ac.in",
                        "iec2017075@iiita.ac.in",
                        "iec2017052@iiita.ac.in",
                        "iec2016011@iiita.ac.in",
                        "iec2016016@iiita.ac.in",
                        "iec2015501@iiita.ac.in",
                        "rit2015021@iiita.ac.in",
                        "rit2015078@iiita.ac.in",
                        "lit2017031@iiita.ac.in",
                        "lit2016030@iiita.ac.in",
                        "ihc2017001@iiita.ac.in",
                        "imb2017002@iiita.ac.in",
                        "pmi2015003@iiita.ac.in",
                        "pwc2015004@iiita.ac.in"
    ];
    
var gymkhanaVolunteerRoll = [
    "iit2017048",
    "iit2017001", 
    "iit2017119",
    "ecm2017004",
    "ihm2017009",
    "iec2017075",
    "iec2017052"
]
    
var president = "iit2015049@iiita.ac.in";

var vicePresident = "iro2017006@iiita.ac.in";

var generalSecretary = "ihm2016001@iiita.ac.in";

var speaker = "pwc2015003@iiita.ac.in";

var culturalSecretary = "iit2016095@iiita.ac.in";

var academicSecretary = "iit2016507@iiita.ac.in";

var technicalSecretary = "iit2016009@iiita.ac.in";

var sportsSecretary = "lit2016032@iiita.ac.in";

var studentWelfareSecretary = "iim2016005@iiita.ac.in";

var presidentialCouncilMember = "pwc2015004@iiita.ac.in";

var presidentialCouncilMember = "iit2015057@iiita.ac.in";
    
    
    
    
app.get("/", function (req, res){
    res.send ("api")
})

app.get("/newuser/:name/:email/:type", function(req, res){
    console.log(req.params);
    var type = req.params.type;
    var email = req.params.email;
    email = email.toLowerCase();
    User.create({name: req.params.name, email: req.params.email, type: req.params.type, credits: "0"}, function(err, newuser){
        if(err){
            console.log (err);
        }
        else{
            if (type == "Gymkhana-Secretary"){
                if (email == "IHM2016001@iiita.ac.in" || email == "ihm2016001@iiita.ac.in"){
                    res.send("done")
                } else {
                    res.send("fakeuser");
                }
            }
            else if (type == "Gymkhana-Member"){
                if(gymkhana.includes(email)){
                    res.send("done");
                } else{
                    res.send("fakeuser");
                }
            }
            else{
                res.send("done");
            }
        }
    });
    
})


app.get("/allusers/:type", function (req, res){
    var type = req.params.type;
    User.find({type: type},function(err, founduser){
        if(err){
            console.log(err);
        } else{
            res.send(founduser);
        }
    });
})


app.get("/newcomplaint/:email/:content/:tag", function (req, res){
    Complaint.create({email: req.params.email, content: req.params.content, tag: req.params.tag, like: "0", dislike: "0", rating: "50", alotedMember1: "not alotted", alotedMember2: "not alotted" }, function(err, newuser){
        if(err){
            console.log (err);
        }
        else{
            res.send(newuser);
        }
    });
})

app.get("/user/info/:email", function (req, res){
    User.findOne({email: req.params.email},function(err, founduser){
        if(err){
            console.log(err);
        } else{
            res.send(founduser);
        }
    });
})


app.get("/alot/volunteer/:id/:first/:second", function(req, res){
    var vol1 = req.params.first;
    var vol2 = req.params.second;
    vol1 = vol1.toLowerCase();
    vol2 = vol2.toLowerCase();
    if ((gymkhanaVolunteerRoll.includes(vol1)) && (gymkhanaVolunteerRoll.includes(vol2))){
        Complaint.findByIdAndUpdate(req.params.id, {alotedMember1:req.params.first, alotedMember2: req.params.second}, function (err, like){
            if(err){
                console.log(err);
            }else{
                res.send("done");
            }
        });
    }
    else{
        res.send("false");
    }
});

app.get("/allcomplaints", function(req, res){
    Complaint.find({}, function (err, allcomplaint){
        if(err){
            console.log(err);
        }else{
            res.send(allcomplaint);
        }
    })
});

app.get("/all/:email", function(req, res){
    var arr = [];
    Complaint.find({}, function (err, allcomplaint){
        if(err){
            console.log(err);
        }else{
            User.find({email: req.params.email}, function(err, founduser){
                if(err){
                    console.log(err)
                }else{
                    arr.push(founduser[0]);
                    var l = allcomplaint.length;
                    var i;
                    var heighest = 0;
                    var temp;
                    var j;
                    for (i = 0; i<l;i++){
                        for(j = i; j < l; j++){
                            if(parseFloat(allcomplaint[j].rating) > heighest){
                                temp = allcomplaint[i];
                                allcomplaint[i] = allcomplaint[j]
                                allcomplaint[j] = temp;
                                heighest = allcomplaint[j].rating;
                            }
                            // arr = arr + JSON.stringify(each)
                        }
                        heighest = 0;
                    }
                    // var arr = "";
                    // arr = arr + JSON.stringify(founduser[0]);
                    allcomplaint.forEach(function(each){
                        arr.push(each);
                    })
  
                    console.log(arr);

                    res.send(arr);
                }
            })
        }
    })
})

app.get("/updatecomplaint/:id/:desc/:status", function(req,res){
    Complaint.findById(req.params.id, function(err, complaint){
            if (err){
                console.log(err);
            }else{
                // desc.complaint.id = req.params.id;
                // desc.save();
                complaint.statusdescription.push(req.params.desc);
                complaint.status.push(req.params.status);
                complaint.save();
                res.send(complaint);
            }
    })
})

app.get("/onecomplaint/:id", function(req, res){
    Complaint.findById(req.params.id, function(err, complaint){
        if (err){
            console.log(err)
        }else{
            res.send(complaint);
        }
    })
});

app.get("/search/complaint/:str", function(req, res){
    var str = req.params.str;
    var str = str.toLowerCase();
    var articles = ["a", "an", "the", "not", "some"];
    var adjectives = ["good", "new", "first", "last", "long", "great", "little", "own", "other", "old", "right", "big", "high", "different", "small", "large", "next", "early", "young", "important", "few", "public", "bad", "same", "able"];
    var propositions = [
        'aboard',
        'about',
        'above',
        'across',
        'after',
        'against',
        'along',
        'amid',
        'among',
        'anti',
        'around',
        'as',
        'at',
        'before',
        'behind',
        'below',
        'beneath',
        'beside',
        'besides',
        'between',
        'beyond',
        'but',
        'by',
        'concerning',
        'considering',
        'despite',
        'down',
        'during',
        'except',
        'excepting',
        'excluding',
        'following',
        'for',
        'from',
        'in',
        'inside',
        'into',
        'like',
        'minus',
        'near',
        'of',
        'off',
        'on',
        'onto',
        'opposite',
        'outside',
        'over',
        'past',
        'per',
        'plus',
        'regarding',
        'round',
        'save',
        'since',
        'than',
        'through',
        'to',
        'toward',
        'towards',
        'under',
        'underneath',
        'unlike',
        'until',
        'up',
        'upon',
        'versus',
        'via',
        'with',
        'within',
        'without'
    ];
    
    var arrToSearch = [];
    var strarr = str.split(" ");
    strarr.forEach(function(each){
        if (!articles.includes(each) && !propositions.includes(each) && !adjectives.includes(each)){
            arrToSearch.push(each)
        }
    });
    
    Complaint.find({}, function(err, complaints){
        if(err){
            console.log(err);
        }else{
            complaints.forEach(function(each){
                var varify = each.content.split(" ");
                var count = 0;
                arrToSearch.forEach(function(eachsearch){
                    if(varify.includes(eachsearch)){
                        count++;
                    }
                })
            })
        }
    })
});

app.get("/likedislike/:id/:like/:dislike", function(req, res){
    Complaint.findByIdAndUpdate(req.params.id, {like: req.params.like, dislike: req.params.dislike}, function (err, like){
        if(err){
            console.log(err);
        }else{
            res.send("done");
        }
    })
})


app.get("/increaselike/:id", function(req,res){
    Complaint.findById(req.params.id, function(err, complaint) {
        var like = complaint.like;
        var dislike = complaint.dislike;
        like = Number(like);
        like = like +1;
        dislike = Number(dislike);
        var rating = Math.ceil((like*100)/(dislike+like));
        rating = String(rating);
        like = String(like);
        Complaint.findByIdAndUpdate(req.params.id, {like: like, rating: rating}, function (err, like){
            if(err){
                console.log(err);
            }else{
                res.send("done");
            }
        })
    })

});


app.get("/decreaselike/:id", function(req,res){
    Complaint.findById(req.params.id, function(err, complaint) {
        var like = complaint.like;
        var dislike = complaint.dislike;
        like = Number(like);
        like = like -1;
        dislike = Number(dislike);
        var rating =  Math.ceil((like*100)/(dislike+like));
        rating = String(rating);
        like = String(like);
        Complaint.findByIdAndUpdate(req.params.id, {like: like, rating: rating}, function (err, like){
            if(err){
                console.log(err);
            }else{
                res.send("done");
            }
        })
    })

});

app.get("/increasedislike/:id", function(req,res){
    Complaint.findById(req.params.id, function(err, complaint) {
        var dislike = complaint.dislike;
        var like = complaint.like;
        dislike = Number(dislike);
        like = Number(like);
        dislike = dislike +1;
        var rating =  Math.ceil((like*100)/(dislike+like));
        rating = String(rating);
        dislike = String(dislike);
        Complaint.findByIdAndUpdate(req.params.id, {dislike: dislike, rating: rating}, function (err, like){
            if(err){
                console.log(err);
            }else{
                res.send("done");
            }
        })
    })

});



app.get("/decreasedislike/:id", function(req,res){
    Complaint.findById(req.params.id, function(err, complaint) {
        var dislike = complaint.dislike;
        var like = complaint.like;
        dislike = Number(dislike);
        like = Number(like);
        dislike = dislike -1;
        var rating =  Math.ceil((like*100)/(dislike+like));
        rating = String(rating);
        dislike = String(dislike);
        Complaint.findByIdAndUpdate(req.params.id, {dislike: dislike, rating: rating}, function (err, like){
            if(err){
                console.log(err);
            }else{
                res.send("done");
            }
        })
    })

});

app.listen(process.env.PORT,process.env.IP,function(){
    console.log("Server is Up..!!"); 
});