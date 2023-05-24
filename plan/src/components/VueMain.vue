<template >
  <div></div>
</template>
<script>
// import axios from "axios";

export default {
  data() {
    return {
      user_id: "",
      user_name: "",
    };
  },
  created() {
    if (this.$route.query.user_id) {
      const userInfo = {
        user_id: this.$route.query.user_id,
        user_name: this.$route.query.user_name,
      };

      //   console.log(this.$route.query.user_id);
      //   console.log(this.$route.query.user_name);

      sessionStorage.setItem("userInfo", JSON.stringify(userInfo));
    }
  },
};
</script>
<style scope>
</style>