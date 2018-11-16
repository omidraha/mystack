Tips
====


Builtin function
----------------

http://golang.org/pkg/builtin/


Introduction to go programming language
---------------------------------------


Define slice
++++++++++++


.. code-block:: go

    package main

    import "fmt"

    func main() {

        var slice_1 []int
        slice_2 := []int {}
        slice_3 := make([]int, 0)

        var slice_4 = []int  {1, 2, 3, 4}
        slice_5 := []int {1, 2, 3, 4}

        slice_6 := make([]int, 0)
        slice_6 = append(slice_6, 1, 2, 3, 4)

        slice_7 := slice_4[:]
        slice_8 := slice_5[0:4]

        slice_9 := make([]int, 4) // [0 0 0 0]
        copy(slice_9, slice_4)

        fmt.Println(slice_1, slice_2, slice_3, slice_4, slice_5, slice_6, slice_7, slice_8, slice_9)
    }
    // [] [] [] [1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]

Notes:

*   The first argument of ``make``, ``append`` and ``copy`` functions must be slice.

*   The second argument of ``copy`` function also must be slice. (As a special case, it also will copy bytes from a string to a slice of bytes.)

*   The ``copy`` function returns the number of elements copied, which will be the minimum of len(src) and len(dst).


.. code-block:: go

    package main

    import "fmt"

    func main() {

        slice_1 := []int {1, 2, 3, 4, 5}
        slice_2 := []int {11, 22}
        slice_3 := []int {111}
        slice_4 := []int {}

        count := copy(slice_1, slice_2)
        fmt.Println(count, slice_1)

        count = copy(slice_3, slice_2)
        fmt.Println(count, slice_3)

        count = copy(slice_4, slice_2)
        fmt.Println(count, slice_4)

    }
    // 2 [11 22 3 4 5]
    // 1 [11]
    // 0 []



SET the GOPATH and GOROOT environments
--------------------------------------

.. code-block:: bash

    $ export GOROOT=/usr/lib/go
    $ export GOPATH=$HOME/go
    $ export PATH=$PATH:$GOROOT/bin:$GOPATH/bin


https://stackoverflow.com/a/43553857
