<template>
  <div id="map" class="w-100" style="height:400px;"></div>
</template>
<script>
export default {
  mounted() {
    window.kakao && window.kakao.maps ? this.initMap() : this.addScript();
  },
  methods: {
    initMap() {
      var container = document.getElementById("map");
      var options = {
        center: new kakao.maps.LatLng(35.20508528749235, 126.80721177615473),
        level: 3
      };
      var map = new kakao.maps.Map(container, options); //마커추가하려면 객체를 아래와 같이 하나 만든다.
      var marker = new kakao.maps.Marker({ position: map.getCenter() });
      marker.setMap(map);
    },
    addScript() {
      const script = document.createElement("script");
      /* global kakao */

      script.onload = () => kakao.maps.load(this.initMap);
      //   console.log(process.env);
      script.src = `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_KAKAO}`;
      document.head.appendChild(script);
    }
  }
};
</script>
//
<style scoped lang="scss">
// // @import "@/assets/stylesheets/index.scss";
// #map {
//   @include css-size(300px, 300px);
// }
//
</style>
