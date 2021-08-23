var axios = require("axios");
const { getTotalRoomsFilter } = require("../../model/connectDB");
const ConnectDB = require('../../model/connectDB');
const GoogleAPI = require('../../model/room/googleAPI');
const Location = require('../../model/room/location');

class AddRoom {
  constructor() {

  }
  getAPIKey = async function () {
      if(this.key === undefined || this.key ===null){
        this.key = await ConnectDB.getAPIKey();
      }
      return this.key;
     
  }
  //add room
  saveLocation = async function (roomID, address, isDeleted) {
    try {

      //Khoi tao location
      let location = new Location(roomID, address, isDeleted);
      //
      console.log("address: " + address);

      //lay toa do
      let key = await this.getAPIKey();
      let coordinates = await GoogleAPI.convertCoordinate(address,key);
      console.log("location: " + location);
      //to-do
      if (coordinates == undefined || coordinates == null) {
        location.lat = 10.3243423;
        location.lng = 100.342342534;
      } else {
        // console.log("location: "+location.toString());
        location.lat = coordinates.lat;
        location.lng = coordinates.lng;
      }

      //save location
      let isSaved = await ConnectDB.saveLocation(location);

      return isSaved;
    } catch (error) {
      console.log("co vo 2");
      console.log(error);
      //
      return false;
    }
  };


  //add room
  updateLocation = async function (roomID, address, isDeleted) {
    try {

      //Khoi tao location
      let location = new Location(roomID, address, isDeleted);
      //
      console.log("address: " + address);

      //lay toa do
      let key = await this.getAPIKey();
      let coordinates = await GoogleAPI.convertCoordinate(address,key);
      console.log("location: " + location);
      //to-do
      if (coordinates == undefined || coordinates == null) {
        location.lat = 10.7581199;
        location.lng = 106.6908249;
      } else {
        // console.log("location: "+location.toString());
        location.lat = coordinates.lat;
        location.lng = coordinates.lng;
      }

      //save location
      let isSaved = await ConnectDB.updateLocation(location);

      return isSaved;
    } catch (error) {
      console.log("co vo 2");
      console.log(error);
      //
      return false;
    }


  };

  //add room
  deleteLocation = async function (roomID) {
    
    try {
     let location = new Location(roomID);
      let isDeleted =await ConnectDB.deleteLocation(location);
      return isDeleted;
    } catch (error) {
      console.log("co vo 2");
      console.log(error);
      return false;
    }
  };

//add room
getTotalRoomsSort = async function () {
  try {
    let count = await ConnectDB.getTotalRoomsSort();
    return count;
  } catch (error) {
    console.log("co vo 2");
    console.log(error);
    //
    return 0;
  }
}
//add room
getTotalRoomsFilter = async function (start, end) {
  try {
    let count = await ConnectDB.getTotalRoomsFilter(start,end);
    return count;
  } catch (error) {
    console.log("co vo 2");
    console.log(error);
    //
    return 0;
  }
};
};

module.exports = new AddRoom();
//   insertLocation = async function () {
//     try {
//       var add = await this.convertCoordinate();
//       console.log("add: " + add);
//       await this.db
//         .collection("location")
//         .doc("/" + this.roomID + "/")
//         .create({
//           roomID: this.roomID,
//           longitude: add.lng,
//           latitude: add.lat,
//           isDeleted: this.isDeleted
//         });
//       console.log("Co vo 1");
//       return true;
//     } catch (error) {
//       console.log("co vo 2");
//       console.log(error);
//       return false;
//     }
//   };
//   updateLocation = async function() {
//     console.log("co vo updateLocation: ");
//     try{
//       var add = await this.convertCoordinate();
//       console.log("Add: "+add);
//       await this.db.collection("location")
//       .doc("/"+this.roomID+"/")
//       .update({
//         roomID:this.roomID,
//         longitude:add.lng,
//         latitude:add.lat,
//         isDeleted:this.isDeleted
//       });
//       return true;
//     }catch(error){
//       return false;
//     }

//   }
//   getLocationByID = async function(){
//     try{
//       let loc = this.db.collection('location').doc(this.roomID);
//       var roomLoc = await loc.get();
//       if(!roomLoc.exists){
//         // console.log('No such document!')
//         return null;
//       }else{
//         // console.log('roomLoc: '+roomLoc.data().latitude);
//         // console.log("Json"+JSON.stringify(roomLoc.data()));
//         return roomLoc.data();
//       }
//       // console.log("loc getLoc: "+loc);
//     }catch(error){
//       return null;
//       // console.log('Loi: '+error);
//     }
//   }
//   //distance unit is: KM
//   search = async function(distance){
//     var locationsNearBy = [];
//     var add = await this.convertCoordinate();
//     var querySnapshot = await this.getLocations(add.lat,add.lng);
//     querySnapshot.forEach((doc) => {

//       var realDistance = this.getDistanceFromLatLonInKm(add.lat,add.lng,doc.data().latitude,doc.data().longitude);
//       console.log(realDistance);
//       if(realDistance<=distance){
//         locationsNearBy.push(new Distance(doc.id,realDistance));
//       }
//     });
//     locationsNearBy.sort(function(disA,disB) {
//       return disA.distance - disB.distance;
//     })
//     console.log("length: "+locationsNearBy.length);
//     //to String
//     var s = '{\"locs\":[';
//     for(let i = 0;i<locationsNearBy.length;i++){
//       s += '{"id":'+locationsNearBy[i].id+',"distance":'+locationsNearBy[i].distance+'},';
//     }
//     s+="]}";
//     console.log(s);
//     return JSON.parse(s);
//   }

//   getLocations = async function(lat,lng){
//     console.log("lat: "+lat);
//     console.log("lng: "+lng);
//     try{
//     let locs = await this.db.collection('location');

//     locs = await locs.where('isDeleted','==',false);
//     console.log("Xay ra loi+++++++");
//     locs = await locs.get();

//     console.log("Locs: "+locs);
//     return locs;
//     }catch(error){
//       console.log(error);
//       return null;
//     }
//   }

//   convertCoordinate = async function () {
//     var result = null;
//     console.log("address get location: "+this.address);
//     await axios
//       .get("https://maps.googleapis.com/maps/api/geocode/json", {
//         params: {
//           address: this.address, //thay doi thanh this
//           key: this.key,
//         },
//       })
//       .then(function (response) {
//         // console.log(response.data.results[0].geometry.location);
//         result = response.data.results[0].geometry.location;
//         console.log("result1: " + result.lat);
//       })
//       .catch(function (error) {
//         console.log("Loi");
//       });
//     console.log("result2: " + result);
//     return result;
//   };
//   getDistanceFromLatLonInKm(lat1, lon1, lat2, lon2) {
//     console.log("coor: "+lat1+":"+lat2+";"+lon1+":"+lon2);
//     var R = 6371; // Radius of the earth in km
//     var dLat = this.deg2rad(lat2 - lat1); // deg2rad below
//     var dLon = this.deg2rad(lon2 - lon1);
//     var a =
//       Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//       Math.cos(this.deg2rad(lat1)) *
//         Math.cos(this.deg2rad(lat2)) *
//         Math.sin(dLon / 2) *
//         Math.sin(dLon / 2);
//     var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//     var d = R * c; // Distance in km
//     console.log("d: "+d);
//     return d;
//   }

//   deg2rad(deg) {
//     return deg * (Math.PI / 180);
//   }

// };
