<template>
  <div class="container" id="locations">
    <div class="filter">
      <label
        ><input
          type="radio"
          v-model="location"
          value="전체"
          class="radio__input"
        />
        전체</label
      >
      <label
        ><input
          type="radio"
          v-model="location"
          value="서울"
          class="radio__input"
        />
        서울</label
      >
      <label
        ><input
          type="radio"
          v-model="location"
          value="광주"
          class="radio__input"
        />
        광주</label
      >
      <label
        ><input
          type="radio"
          v-model="location"
          value="대전"
          class="radio__input"
        />
        대전</label
      >
      <label
        ><input
          type="radio"
          v-model="location"
          value="구미"
          class="radio__input"
        />
        구미</label
      >
    </div>
  </div>
</template>

<script>
export default {
  data: () => ({
    // location:"전체";
  })
};
</script>

<style scoped>
/* radio-btns  */
.radio__input {
  -webkit-appearance: none;
  -moz-appearance: none;
  -ms-appearance: none;
  -o-appearance: none;
  appearance: none;
  position: relative;
  /* top: 13.33333px; */
  /* right: 13; */
  bottom: 0;
  left: 0;
  height: 15px;
  width: 15px;
  transition: all 0.15s ease-out 0s;
  background: #cbd1d8;
  border: none;
  color: #fff;
  cursor: pointer;
  display: inline-block;
  /* margin-right: 0.5rem; */
  outline: none;
  /* position: relative; */
  /* z-index: 1000; */
}
.radio__input:hover {
  background: #9faab7;
}
.radio__input:checked {
  background: #636da6;
}
.radio__input:checked::before {
  height: 10px;
  width: 10px;
  position: absolute;
  content: "✔";
  display: inline-block;
  font-size: 26.66667px;
  text-align: center;
  line-height: 5px;
}
.radio__input:checked::after {
  -webkit-animation: click-wave 0.65s;
  -moz-animation: click-wave 0.65s;
  animation: click-wave 0.65s;
  background: #40e0d0;
  content: "";
  display: block;
  position: relative;
  z-index: 100;
}
.radio__input.radio {
  border-radius: 50%;
}
.radio__input.radio::after {
  border-radius: 50%;
}
.filter {
  display: inline-block;
}
</style>
