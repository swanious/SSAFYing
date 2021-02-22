<template>
  <div class="card-body d-flex justify-content-around">
    <!-- facebook -->
    <div class="rounded-circle official-site-photo">
      <a href="https://developers.facebook.com/blog/">
        <img
          src="https://images.vexels.com/media/users/3/137253/isolated/preview/90dd9f12fdd1eefb8c8976903944c026-facebook-icon-logo-by-vexels.png"
          alt=""
          class="w-100"
        />
      </a>
    </div>
    <!-- naver -->
    <div class="rounded-circle official-site-photo">
      <a href="https://d2.naver.com/home">
        <img
          src="https://res-5.cloudinary.com/crunchbase-production/image/upload/c_lpad,f_auto,q_auto:eco/v1504499304/in36bktetqoapibgeabo.png"
          alt=""
          class="w-100"
        />
      </a>
    </div>
    <!-- woowa -->
    <div class="rounded-circle official-site-photo">
      <a href="https://woowabros.github.io/">
        <img
          src="https://image.rocketpunch.com/company/118/woowahan_logo_1595824139.png?s=400x400&t=inside"
          alt=""
          class="w-100"
        />
      </a>
    </div>
    <!-- kakao -->
    <div class="rounded-circle official-site-photo">
      <a href="https://tech.kakao.com/blog/">
        <img
          src="https://t1.kakaocdn.net/kakaocorp/corp_thumbnail/Kakao.png"
          alt=""
          class="w-100"
        />
      </a>
    </div>
    <!-- toss -->
    <div class="rounded-circle official-site-photo">
      <a href="https://blog.toss.im/tag/%ED%86%A0%EC%8A%A4-%EA%B0%9C%EB%B0%9C/">
        <img
          src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAADICAMAAACahl6sAAAAkFBMVEX///8AUP/8/f8DUv+xyf/o7//7/P8LWP8PWv8sbv8JVv/x9f+70P/2+f8cY//v9P8YYP81dP9mlv/i6/91oP/F1/9Kg//Z5f9Uiv+ux/8gZv9AfP+AqP/P3v9wnf/e6P9hk/+Nsf+cu/8vcP+fvf85d/+Fq//C1f+evP8laf/S4P9XjP+Vtv98pf9Nhf+MsP+nRq2+AAAFyklEQVR4nO2diXaqMBCGyyoIKOKCgqBWi9W6vP/bXXFprRYTIMkM9+R7gvyHzJJJmHl7k0gkEolEIpFIJBKJRCKRSCRFqF7X6G8TVz8zTELDcEzoRZXEM5LJIv2K7Y5vWa0zlu9HvdF8/+nOHBV6fVQ44WQ/tv228jeaFcXBbmjg/jZmX9+POkUa7tUs5+9bD3q5Baj9SWC3iCK+xXQOxwShFscdLMmf4lHLeNVHZTBqfxdbJVVctdiDIZrPYm6ntlZJxhn/y3WgJeSYySCqruKMddDhpYT7ujJyWgcXdoMZiw0DGTlWkMCZvafHNWzjkSgzgHSEQTVPVYQW6xD7y1vZTGXkWGlfuI4Z489x5cMVaymq+8FDxgn/KNITe7sOJx2K0p7PhOkwUvrcsAIfa0E6wgNPGSeiiRBDGfIyjx/8nQA/7LL3us9YU+4mr7NIrci0B5yVCNLBW4n3zs/tilTiZFyiuXAlzqDsobymkoyP7xKt4+S7djziiXgdp3gy+T90KMpmyFqHt4fQoSgx4wOKeeSaJr4gYOq61JVIv/uL9o6lEF1cHHwiYpjUJ0s4HSczYVZc6Y8gdSjalFE0cQJQHadowmZzqQsYx3vHqMtCiOtD61A0Fp4r7EHLOGGHtXU4c2gRZwa17R3eQM506uZca8BI+Iug3nW2EUMLuOHX+iTqlOH1R01qfRIEnvebOp8Ez8bKqe64MG2sE5vKsQSNx7qgLSrq6PKuuZclrphx7XCEwh8st5KOUETRvRxpFXM3U+hlP2NXqahgCiE32hXKdQ7s6baAoPze+sRm6Wd6pcsQfQynqWfK+60MVUz/ISupI2T1dok143IXJuoAesFFbMo54ETUhWdpyhkJxlh4o1TiOEQYC2+UOSea0BXSV5TJgDF/ECWit3bUH0Sx6E/uCa5z4QP0eSPeGHKB2m1t0caQCwNaIVPolRKYU/rfGb4D7m8OlNnWAmna+w1lIDH4P1esCeXZaoLyYHiPTSXEG0Ovk0iHKrSvMWcnF6iEqIjz9xtUQtCecO+gErLD7nsVOiEOqnudAmiEuGBvskpA4X5xH0RuUATEWQNMnSpFeW+AqdMkjQ2I6jnkND7BH9VzyAerDHqJdBCPut0mBBGa4kMjgghNOQh58eQGsUBn4LyieiImPTDXoZ6+l4R4HdqQnUV0Wk3ZWcSLnqbsLOLVW1N2FukytIu+nHWFdD3dkGhINhHslesbS8KpqhGH9RxSFGlIBk/OGJtQBsohPTzzvqBXSAlpZzWj6qAoLZ2ws5oS1okFlD30Cik5EnQ0xflGpCflTXG+KakQ9NkM50v87eJVydfqBYsAybXiF6nEWHSm0vxRtjbMNwdHlCG/nfvbRKw4S67nfBypMfGD/FW71qLA/fHZKK4byL/rPi+z3cvCXw5iiODhE/kJ4KOJtOPVY9JvwkfMzZak48FEWqPVH3nADLrGomXk1/33JtKOJ38X8qCf/FP0GLh7I3DaVEVZGbALtkhp79v9bcJy90J2AprpE5OTE+HVI0XT1634IP8c+6DpEnh512TNSQ1RAX9r96new+cXblo8Ib+vA2s0QOOxLsf1Dl1TVx0o2x9Tvf0zbO0wpPsDS81AzKRHDoU53XRB/WoepD1KRPvDiFnihziAhjXWO5cWmlvRBt/OOA0uGIp93qyl3BocuiJfzmtzjj2yhTXO5KxDoBLOOoQp4a5DUJvcdiqgh7yAxsUC2v3mcG8lLaQBcw7n5t62Lqx3PM9269ooESXjjWcDfGsgeFQEp5EES4pDHmN4DIlozes3m6sA67EdSm8FNOHGm7AcpOIPxA1VeILdaJvWeA07NY3NsKHCaq1AGIx/elGtFUrNgVzWeIJCRk6NEWmbdI1mRNoZx03t0kPr/NEuxDcY0Sw5RtAfHRNwCy+g1GBHtCqu/BejNm+8Hn7aRTXLkczjONpZA8fRSiQSiUQikUgkEolEIpFIJMz5B2QojhQL6QXjAAAAAElFTkSuQmCC"
          alt=""
          class="w-100"
        />
      </a>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
.official-site-photo {
  width: 56px;
  height: 56px;
  position: relative;
  border-width: 2px !important;
}
</style>
