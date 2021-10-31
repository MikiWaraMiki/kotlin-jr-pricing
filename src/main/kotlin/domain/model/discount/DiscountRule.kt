package domain.model.discount

interface DiscountRule {
    /**
     * 割引が適用可能か検証する
     */
    fun can(): Boolean
}