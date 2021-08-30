var axios = require("axios");
class GoogleAPI {
  constructor() {
  }
  //su dung
  convertCoordinate = async function(address,key) {
   
    var result = null;
    console.log("address get location: "+address);
    await axios
      .get("https://maps.googleapis.com/maps/api/geocode/json", {
        params: {
          address: address, //thay doi thanh this
          key: key,
        },
      })
      .then(function (response) {
        // console.log(response.data.results[0].geometry.location);
        result = response.data.results[0].geometry.location;
       
        console.log("result1: " + result.lat);
      })
      .catch(function (error) {
        console.log("Loi");
      });
    console.log("result2: " + result);
    return result;
  };

  getDistanceFromLatLonInKm(lat1, lon1, lat2, lon2) {
    // console.log("coor: "+lat1+":"+lat2+";"+lon1+":"+lon2);
    var R = 6371; // Radius of the earth in km
    var dLat = this.deg2rad(lat2 - lat1); // deg2rad below
    var dLon = this.deg2rad(lon2 - lon1);
    var a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(this.deg2rad(lat1)) *
        Math.cos(this.deg2rad(lat2)) *
        Math.sin(dLon / 2) *
        Math.sin(dLon / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    var d = R * c; // Distance in km
    // console.log("d: "+d);
    return d;
  }

  deg2rad(deg) {
    return deg * (Math.PI / 180);
  }

};
module.exports = new GoogleAPI();
