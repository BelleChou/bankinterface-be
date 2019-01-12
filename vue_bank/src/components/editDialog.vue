<template>
	<div class="dealEdit" v-if="isShow">
		<slot> </slot>
		
	</div>
	
	
</template>


<script>
	export default{
		props:{
			isShow:{
				type:Boolean,
				default:true
			}
		}
		
		
	}
	
	
</script>