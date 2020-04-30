package pl.pjatk.s16604.proj1.models

data class Upgrade (val icon: String, val upgradeName: String, var amount: Int, val income: Long, var cost:Long, val multiplier: Double) {

    fun addUpgrade(){
        this.amount++
        this.cost= (multiplier*cost).toLong()
    }

    fun calcPoints(): Double {
        return this.amount*this.multiplier*10
    }

    fun calcIncome(): Long {
        return this.income * this.amount
    }


}