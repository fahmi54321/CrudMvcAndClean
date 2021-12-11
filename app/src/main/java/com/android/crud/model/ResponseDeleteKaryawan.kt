package com.android.crud.model

import com.google.gson.annotations.SerializedName

data class ResponseDeleteKaryawan(

	@field:SerializedName("data")
	val data: Any? = null,

	@field:SerializedName("meta")
	val meta: MetaDelete? = null
)

data class MetaDelete(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
