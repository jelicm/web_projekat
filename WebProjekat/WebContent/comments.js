var app = new Vue({
    el: '#comments',
    data:{
		komentari: {}
    },

	mounted() {
		axios
			.get("rest/comments")
			.then((response) => {
				this.komentari = response.data;
			});
	}
})