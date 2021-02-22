<template>
  <div class="card" v-if="weatherItem">
    <div class="card-header">
      오늘의 날씨 정보 👀
    </div>
    <div class="card-body">
      현재
      <h5 class="card-title">
        {{ Math.round(weatherItem.main.temp - 273.15) }}℃
        {{ weatherItem.weather[0].description }}
      </h5>
      <div class="d-flex">
        <div>
          체감 :
          <b>{{ Math.round(weatherItem.main.feels_like - 273.15) }}</b> ℃
        </div>
        <div class="ml-2">
          최저 :
          <b>
            {{ Math.round(weatherItem.main.temp_min - 273.15) }}
          </b>
          ℃
        </div>
        <div class="ml-2">
          최고 :
          <b> {{ Math.round(weatherItem.main.temp_max - 273.15) }} </b>℃
        </div>
      </div>
      <div class="d-flex justify-content-center">
        <img :src="imgSrc" alt="" />
      </div>
      <div class="d-flex justify-content-center">
        <button href="https://weather.naver.com/" class="weather__btn">
          네이버 날씨로 이동
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    weatherItem: {
      type: Object
    }
  },
  computed: {
    imgSrc() {
      // console.log(this.weatherItem);
      return `http://openweathermap.org/img/wn/${this.weatherItem.weather[0].icon}@2x.png`;
    }
  }
};
</script>

<style scoped>
.weather__btn {
  margin-top: 2rem;
  background-color: var(--color7) !important;
  color: #ffffff !important;
  /* font-weight: bold; */
  font-size: 18px;
  width: 80%;
  height: 2.5rem;
  border-radius: 3px;
  word-spacing: 2px;
}
</style>
