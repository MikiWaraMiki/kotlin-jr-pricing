package jrpricing.demo.first.domain.model.discount

class DiscountName(
    name: String
) {
    private val value: String

    init {
        if (name == "") throw IllegalArgumentException("割引名の入力は必須です")

        if (name.length > 100) throw IllegalArgumentException("割引名は100文字以内で入力してください")

        value = name
    }
}