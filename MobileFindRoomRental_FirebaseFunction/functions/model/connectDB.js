
const Location = require('./room/location');

const admin = require('firebase-admin');
var serviceAccount = require("../permissions.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});
const db = admin.firestore();

class ConnectDB {
  constructor() {
  }
  getAPIKey = async function () {
    var key;

    let docRef = db.collection("api").doc("key");
    await docRef.get().then((doc) => {
      if (doc.exists) {
        console.log("Document data:", doc.data().key);
        key = doc.data().key;
      } else {
        // doc.data() will be undefined in this case
        console.log("No such document!");
      }
    }).catch((error) => {
      key = null;
      console.log("Error getting document:", error);
    });
    console.log(key);

    return key;
  }
  //su dung
  saveLocation = async function (location) {
    console.log("saveLocation: " + location.roomID + " : " + location.lng + " : " + location.lat + " : " + location.isDeleted);
    try {
      await db
        .collection("locations")
        .doc(location.roomID)
        .create({
          roomID: location.roomID,
          address: location.address,
          isDeleted: location.isDeleted,
          latitude: location.lat,
          longitude: location.lng


        });
      console.log("Co vo 1");
      return true;
    } catch (error) {
      console.log("co vo 2");
      console.log(error);
      return false;
    }
  };

  //su dung
  updateLocation = async function (location) {
    console.log("saveLocation: " + location.roomID + " : " + location.lng + " : " + location.lat + " : " + location.isDeleted);
    try {
      await db
        .collection("locations")
        .doc(location.roomID)
        .update({
          roomID: location.roomID,
          address: location.address,
          isDeleted: location.isDeleted,
          latitude: location.lat,
          longitude: location.lng
        });
      console.log("Co vo 1");
      return true;
    } catch (error) {
      console.log("co vo 2");
      console.log(error);
      return false;
    }
  };
  //su dung
  deleteLocation = async function (location) {
    console.log("saveLocation: " + location.roomID + " : " + location.lng + " : " + location.lat + " : " + location.isDeleted);
    try {
      await db
        .collection("locations")
        .doc(location.roomID)
        .update({
          isDeleted: true
        });
      console.log("Co vo 1");
      return true;
    } catch (error) {
      console.log("co vo 2");
      console.log(error);
      return false;
    }
  };


  //su dung
  getAllLocations = async function () {



    var locationsList = [];

    try {
      const locationRef = db.collection('locations');
      const snapshot = await locationRef.where('isDeleted', '==', false).get();
      for (let doc of snapshot.docs) {
        console.log(doc.id, '=>', doc.data().address);
        let location = new Location(doc.id, doc.data().address, doc.data().isDeleted, doc.data().latitude, doc.data().longitude);
        console.log("location: " + location.toString());
        locationsList.push(location);
      }

      // snapshot.forEach(doc => {
      //   console.log(doc.id, '=>', doc.data());
      //   let location = new Location(doc.id, doc.data().address, doc.data().isDeleted, doc.data().latitude, doc.data().longitude);
      //   console.log("location: " + location.toString());
      //   locationsList.push(location);
      // });
      console.log("Co vo 1");
      return locationsList;
    } catch (error) {
      console.log("co vo 2");
      console.log(error);
      return null;
    }
  };
  /* User Json
  {
     "users":[
        {
           "user":{
              "userUid":"58039485dsfklaj",
              "displayName":"Trịnh Tiến",
              "email":"trinhtien6236@gmail.com",
              "photoUrl":"tiéndfa"
           }
        },
        {
           "user":{
              "userUid":"dlfkjasldfj549",
              "displayName":"Trịnh Tiến",
              "email":"trinhtien6236@gmail.com",
              "photoUrl":"tiéndfa"
           }
        }
     ]
  }
  */
  getUser = async function (userUid) {
    let user_json = '{';
    await admin
      .auth()
      .getUser(userUid)
      .then((userRecord) => {
        // See the UserRecord reference doc for the contents of userRecord.
        // console.log("email: "+userRecord.email);
        // console.log("Successfully fetched user data:"+ userRecord.toJSON());

        user_json += '"userUid":"' + userRecord.uid + '"';
        user_json += ',"displayName":"' + userRecord.displayName + '"';
        user_json += ',"email":"' + userRecord.email + '"';
        user_json += ',"photoUrl":"' + userRecord.photoURL + '"';
        user_json += '}'

      })
      .catch((error) => {

        console.log('Error fetching user data:', error);
        return null;
      });
    return user_json;
  }
  listAllUsers = async function (nextPageToken) {
    // List batch of users, 1000 at a time.
    let user_json = '{"users":['
    await admin
      .auth()
      .listUsers(1000, nextPageToken)
      .then((listUsersResult) => {
        listUsersResult.users.forEach((userRecord) => {
          // console.log('user', userRecord.toJSON());
          console.log("userUid: " + userRecord.uid);
          user_json += '{"user":{';
          user_json += '"userUid":"' + userRecord.uid + '"';
          user_json += ',"displayName":"' + userRecord.displayName + '"';
          user_json += ',"email":"' + userRecord.email + '"';
          user_json += ',"photoUrl":"' + userRecord.photoURL + '"';
          user_json += '}},'
        });
        if (listUsersResult.pageToken) {
          // List next batch of users.
          listAllUsers(listUsersResult.pageToken);
          console.log("Co vo list All Users if");
        }
      })
      .catch((error) => {
        console.log('Error listing users:', error);
        return null;
      });
    user_json = user_json.slice(0, user_json.length - 1);
    user_json += ']}';

    return user_json;
  };
  deleteUser = async function (userUid) {
    var isDeleted = false;
    await admin
      .auth()
      .deleteUser(userUid)
      .then(() => {
        console.log('Successfully deleted user');
        isDeleted = true;
      })
      .catch((error) => {
        console.log('Error deleting user:', error);
        isDeleted = false;
      });
    return isDeleted;
  }

  //get total room sort
  getTotalRoomsSort = async function () {
    let count = 0;
    await db.collection("rooms").where("isDeleted", "==", false).get().then((querySnapshot) => {
      count = querySnapshot.size;
    }).catch((error) => {
      console.log("Error getting documents: ", error);
    });
    return count
  };
  getTotalRoomsFilter = async function (start, end) {
    let count = 0;
    console.log("start: " + start);
    console.log("end: " + end);
    await db.collection("rooms").where("dateCreated", ">=", start).where("dateCreated", "<", end)
      .get()
      .then(
        (querySnapshot) => {
          querySnapshot.forEach((doc) => {
            if (doc.get("isDeleted") == false) {
              count++;
            }else{
              console.log("isDeleted == true")
            }
            // doc.data() is never undefined for query doc snapshots
            // console.log(doc.id, " => ", doc.data());

          })
        })
      .catch((error) => {
        console.log("Error getting documents: ", error);
      });
    return count;
  }

};
module.exports = new ConnectDB();

  // updateLocation = async function() {
  //   console.log("co vo updateLocation: ");
  //   try{
  //     var add = await this.convertCoordinate();
  //     console.log("Add: "+add);
  //     await db.collection("location")
  //     .doc("/"+this.roomID+"/")
  //     .update({
  //       roomID:this.roomID,
  //       longitude:add.lng,
  //       latitude:add.lat,
  //       isDeleted:this.isDeleted
  //     });
  //     return true;
  //   }catch(error){
  //     return false;
  //   }

  // }
  // getLocationByID = async function(){
  //   try{
  //     let loc = db.collection('location').doc(this.roomID);
  //     var roomLoc = await loc.get();
  //     if(!roomLoc.exists){
  //       // console.log('No such document!')
  //       return null;
  //     }else{
  //       // console.log('roomLoc: '+roomLoc.data().latitude);
  //       // console.log("Json"+JSON.stringify(roomLoc.data()));
  //       return roomLoc.data();
  //     }
  //     // console.log("loc getLoc: "+loc);
  //   }catch(error){
  //     return null;
  //     // console.log('Loi: '+error);
  //   }
  // }
  // //distance unit is: KM
  // search = async function(distance){
  //   var locationsNearBy = [];
  //   var add = await this.convertCoordinate();
  //   var querySnapshot = await this.getLocations(add.lat,add.lng);
  //   querySnapshot.forEach((doc) => {

  //     var realDistance = this.getDistanceFromLatLonInKm(add.lat,add.lng,doc.data().latitude,doc.data().longitude);
  //     console.log(realDistance);
  //     if(realDistance<=distance){
  //       locationsNearBy.push(new Distance(doc.id,realDistance));
  //     }
  //   });
  //   locationsNearBy.sort(function(disA,disB) {
  //     return disA.distance - disB.distance;
  //   })
  //   console.log("length: "+locationsNearBy.length);
  //   //to String
  //   var s = '{\"locs\":[';
  //   for(let i = 0;i<locationsNearBy.length;i++){
  //     s += '{"id":'+locationsNearBy[i].id+',"distance":'+locationsNearBy[i].distance+'},';
  //   }
  //   s+="]}";
  //   console.log(s);
  //   return JSON.parse(s);
  // }

  // getLocations = async function(lat,lng){
  //   console.log("lat: "+lat);
  //   console.log("lng: "+lng);
  //   try{
  //   let locs = await db.collection('location');

  //   locs = await locs.where('isDeleted','==',false);
  //   console.log("Xay ra loi+++++++");
  //   locs = await locs.get();

  //   console.log("Locs: "+locs);
  //   return locs;
  //   }catch(error){
  //     console.log(error);
  //     return null;
  //   }
  // }

  // //su dung
  // convertCoordinate = async function (address) {
  //   var result = null;
  //   console.log("address get location: "+this.address);
  //   await axios
  //     .get("https://maps.googleapis.com/maps/api/geocode/json", {
  //       params: {
  //         address: address, //thay doi thanh this
  //         key: this.key,
  //       },
  //     })
  //     .then(function (response) {
  //       // console.log(response.data.results[0].geometry.location);
  //       result = response.data.results[0].geometry.location;

  //       console.log("result1: " + result.lat);
  //     })
  //     .catch(function (error) {
  //       console.log("Loi");
  //     });
  //   console.log("result2: " + result);
  //   return result;
  // };
  // getDistanceFromLatLonInKm(lat1, lon1, lat2, lon2) {
  //   console.log("coor: "+lat1+":"+lat2+";"+lon1+":"+lon2);
  //   var R = 6371; // Radius of the earth in km
  //   var dLat = this.deg2rad(lat2 - lat1); // deg2rad below
  //   var dLon = this.deg2rad(lon2 - lon1);
  //   var a =
  //     Math.sin(dLat / 2) * Math.sin(dLat / 2) +
  //     Math.cos(this.deg2rad(lat1)) *
  //       Math.cos(this.deg2rad(lat2)) *
  //       Math.sin(dLon / 2) *
  //       Math.sin(dLon / 2);
  //   var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  //   var d = R * c; // Distance in km
  //   console.log("d: "+d);
  //   return d;
  // }

  // deg2rad(deg) {
  //   return deg * (Math.PI / 180);
  // }


