package co.naiveimpl

var c1Limit: Double = 1000000000.0
var c2Limit: Double = 1000000000.0


fun main() {
    var c1 = true
    var c2 = true

    var c1Count = 0.0
    var c2Count = 0.0

    while(c1 || c2) {
        if(c1) {
            if(c1Count < c1Limit) {
                c1Count += .7
            } else {
                c1 = false
                println("C1 Terminated")
            }
        }
        
        if(c2) {
            if(c2Count < c2Limit) {
                c2Count++
            } else {
                c2 = false
                println("C2 Terminated")
            }
        }
    }
    println("Completed")
}

