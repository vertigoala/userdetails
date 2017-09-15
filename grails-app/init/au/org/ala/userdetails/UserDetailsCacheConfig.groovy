package au.org.ala.userdetails

config = {
    defaults {
        eternal false
        overflowToDisk false
        maxElementsInMemory 10000
        timeToLiveSeconds 3600
    }
    cache {
        name 'dailyCache'
        timeToLiveSeconds (3600 * 24)
    }
}