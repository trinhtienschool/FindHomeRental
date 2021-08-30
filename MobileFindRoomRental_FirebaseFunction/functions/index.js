const AddRoom = require('./controller/room/AddRoom');
const GetUser = require('./controller/user/GetUser');
const SearchEngine = require('./controller/room/searchEngine');
const functions = require('firebase-functions');

const express = require('express');
const app = express();
const cors = require('cors');
app.use(cors({origin:true}));

app.get('/hello-world',(req,res)=>{
    return res.status(200).send('Hello world');
});

//add location
app.post('/api/add-location',(req,res)=>{
    console.log("body: "+req.body);
    console.log(req.body.address);
    (async() => {
        let isSuccess =await AddRoom.saveLocation(req.body.roomID,req.body.address,req.body.isDeleted);
        console.log("success: "+isSuccess);
        if(isSuccess){
            return res.status(200).send('true');
        }else{
            return res.status(500).send('false');
        }    
    })();
});
//update room
app.put('/api/update-location',(req,res)=>{
    console.log("RoomID: "+req.query.roomID);
    console.log("Body: "+req.body.address);
    (async() => {
        let isSuccess =await AddRoom.updateLocation(req.query.roomID,req.body.address,req.body.isDeleted);
        console.log("success: "+isSuccess);
        if(isSuccess){
            return res.status(200).send('true');
        }else{
            return res.status(500).send('false');
        }    
    })();
});
//delete room
app.delete('/api/delete-location',(req,res)=>{
    console.log("body: "+req.body);
    console.log(req.query.roomID);
    (async() => {
        let isSuccess =await AddRoom.deleteLocation(req.query.roomID,null,true);
        console.log("success: "+isSuccess);
        if(isSuccess){
            return res.status(200).send('true');
        }else{
            return res.status(500).send('false');
        }    
    })();
});

//search roomID
app.get('/api/search-room',(req,res)=>{
    console.log(req.body.address);
    (async() => {
        let roomIDs = await SearchEngine.getNearbyRoomID(req.query.address,req.query.distance);
        console.log("success: "+roomIDs);
        if(roomIDs!==null){
            res.setHeader("Content-Type","application/json");
            console.log("Da gui");
            return res.status(200).send(roomIDs);
        }else{
            return res.status(500).send();
        }    
    })();
});
//search roomID
app.get('/api/get-api',(req,res)=>{
    console.log(req.body.address);
    (async() => {
         await SearchEngine.getAPIKey();
       
    })();
});
//get a users
app.get('/api/get-user',(req,res)=>{
    console.log(req.query.userUid);
    (async()=>{
       
        res.setHeader("Content-Type","application/json");
        let user = await GetUser.getUser(req.query.userUid);
        console.log("user; "+user);
        if(user !=null || user !=undefined){
            return res.status(200).send(user);
        }else return res.status(500).send();
    })();
   
});
//get all users
app.get('/api/list-all-users',(req,res)=>{
    console.log(req.body.address);
    (async()=>{
        res.setHeader("Content-Type","application/json");
        let user_object_json =await GetUser.listAllUsers();
        if(user_object_json !=null){
           return res.status(200).send(user_object_json);
        }else return res.status(500).send();
    })();
   
});

// delete user
app.delete('/api/delete-user',(req,res)=>{
    console.log(req.body.address);
    (async()=>{
       
        let isDeleted =await GetUser.deleteUser(req.query.userUid);
        console.log("deleted: "+isDeleted)
        if(isDeleted == true){
            return res.status(200).send('true');
        }else return res.status(500).send('false');
    })();
   
});

//get  total room sort
app.get('/api/get-total-room-sort',(req,res)=>{
   
    (async()=>{
       
        // res.setHeader("Content-Type","application/json");
        let count = await AddRoom.getTotalRoomsSort();
        console.log("count; "+count);
        return res.status(200).send(count+"");
    })();
   
});
//get  total room filter
app.get('/api/get-total-room-filter',(req,res)=>{
    console.log(req.query.start);
    console.log(req.query.end);
    let start = new Date(req.query.start);
    let end =new Date(req.query.end);
    (async()=>{
        let count = await AddRoom.getTotalRoomsFilter(start,end);
        console.log("count; "+count);
        return res.status(200).send(count+"");
    })();
   
});
exports.app = functions .region('asia-southeast1').https.onRequest(app);