package com.example.debtcounter

class Human(private var _name: String, private var _amount : Double, private var _total : Double) {
  private var name: String = _name
  private var amount: Double = _amount
  private var total: Double = _total

  fun setName(_name: String){
    name = _name
  }

  fun getName():String{
    return name
  }

  fun setAmount(_amount: Double){
    amount = _amount
  }
  fun getAmount():Double{
    return amount
  }

  fun setTotal(_total: Double){
    total = _total
  }
  fun getTotal():Double{
    return total;
  }

  fun amountInc():Double{
    return amount.plus(0.5)
  }

  fun amountDec():Double{
    return amount.minus(0.5)
  }
}