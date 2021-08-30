
const GoogleAPI = require('./googleAPI');
// const ConnectDB = require('../connectDB');
class Location {
  constructor(roomID,address,isDeleted,lat,lng) {
    this.roomID = roomID;
    this.address = address;
    this.isDeleted = isDeleted;
    this.lat = lat;
    this.lng = lng;
  }

  //su dung
  saveLocation = async function () {
    console.log("address: "+this.address);
    try {
      let location =await GoogleAPI.convertCoordinate(this.address);
      console.log("location: "+location);
      //to-do
      if(location == undefined || location==null){
        this.lat =10.3243423;
        this.lng = 100.342342534;
      }else{
      // console.log("location: "+location.toString());
      this.lat = location.lat;
      this.lng = location.lng;
      }
      let isSave =await ConnectDB.saveLocation(this);
      return isSave;
    } catch (error) {
      console.log("co vo 2");
      console.log(error);
      return false;
    }
  };


  updateLocation = async function () {
    console.log("address: "+this.address);
    try {
      let location =await GoogleAPI.convertCoordinate(this.address);
      console.log("location: "+location);
      //to-do
      if(location == undefined || location==null){
        this.lat =10.3243423;
        this.lng = 100.342342534;
      }else{
      // console.log("location: "+location.toString());
      this.lat = location.lat;
      this.lng = location.lng;
      }
      let isSave =await ConnectDB.updateLocation(this);
      return isSave;
    } catch (error) {
      console.log("co vo 2");
      console.log(error);
      return false;
    }
  };
  deleteLocation = async function () {
    console.log("address: "+this.address);
    try {
     
      let isSave =await ConnectDB.deleteLocation(this);
      return isSave;
    } catch (error) {
      console.log("co vo 2");
      console.log(error);
      return false;
    }
  };

   //su dung
   searchNearByRoomID = async function (distanceSearch) {
     var locationsNearBy = [];
    try {
      let locationSearch =await GoogleAPI.convertCoordinate(this.address);
      let allLocations =await ConnectDB.getAllLocations();
      for(let i=0;i<allLocations.length;i++){
        let a_location = allLocations[i];
        let lat = a_location.lat;
        let lng = a_location.lng;
        let distance =  GoogleAPI.getDistanceFromLatLonInKm(locationSearch.lat,locationSearch.lng,lat,lng);
        if(distance<=distanceSearch){
          let location_distance = Object.assign([],distance,a_location);
          locationsNearBy.push(location_distance);
        }
      }
      return locationsNearBy;
    } catch (error) {
      console.log("co vo 2");
      console.log(error);
      return null;
    }
  };
};
module.exports = Location;

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

//   //su dung
//   setCoordinate = async function(address){
//     try{
//     let coordinate = await GoogleAPI.convertCoordinate(address);
//     this.lat = coordinate.lat;
//     this.lng = coordinate.lng;
//     return true;
//     }catch(error){
//       console.log(error);
//       return false;
//     }
//   }
//   //su dung
//   convertCoordinate = async function (address) {
//     var result = null;
//     console.log("address get location: "+this.address);
//     await axios
//       .get("https://maps.googleapis.com/maps/api/geocode/json", {
//         params: {
//           address: address, //thay doi thanh this
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
