    #       
    # Sum of Absolute Differences Algorithm
    #       
    # Authors:
    #	Basil Feitknecht, Camil Schmid
    #       

.text

main:
    # Initializing data in memory...
    # Store in $s0 the address of the first element in memory
    # lui sets the upper 16 bits of the specified register, lower 16 to zero
    # ori ors together the given immediate to set lower ones
    lui     $s0,            0x0000                  # Address of first element in the vector
    ori     $s0,            0x0000

    # left_image 			= 		   {5, 16, 7, 1, 1, 13, 2, 8, 10}
    addi    $t0,            $0,         5           # left_image[0]
    sw      $t0,            0($s0)
    addi    $t0,            $0,         16          # left_image[1]
    sw      $t0,            4($s0)
    addi    $t0,            $0,         7           # left_image[2]
    sw      $t0,            8($s0)
    addi    $t0,            $0,         1           # left_image[3]
    sw      $t0,            12($s0)
    addi    $t0,            $0,         1           # left_image[4]
    sw      $t0,            16($s0)
    addi    $t0,            $0,         13          # left_image[5]
    sw      $t0,            20($s0)
    addi    $t0,            $0,         2           # left_image[6]
    sw      $t0,            24($s0)
    addi    $t0,            $0,         8           # left_image[7]
    sw      $t0,            28($s0)
    addi    $t0,            $0,         10          # left_image[8]
    sw      $t0,            32($s0)

    addi    $s0,            $s0,        36          # Move to the next array

    # right_image 			= 		   {4, 15, 8, 0, 2, 12, 3, 7, 11}
    addi    $t0,            $0,         4           # right_image[0]
    sw      $t0,            0($s0)
    addi    $t0,            $0,         15          # right_image[1]
    sw      $t0,            4($s0)
    addi    $t0,            $0,         8           # right_image[2]
    sw      $t0,            8($s0)
    addi    $t0,            $0,         0           # right_image[3]
    sw      $t0,            12($s0)
    addi    $t0,            $0,         2           # right_image[4]
    sw      $t0,            16($s0)
    addi    $t0,            $0,         12          # right_image[5]
    sw      $t0,            20($s0)
    addi    $t0,            $0,         3           # right_image[6]
    sw      $t0,            24($s0)
    addi    $t0,            $0,         7           # right_image[7]
    sw      $t0,            28($s0)
    addi    $t0,            $0,         11          # right_image[8]
    sw      $t0,            32($s0)


    # Loop over the elements of left_image and right_image
    addi    $s1,            $0,         0           # $s1 = i = 0
    addi    $s2,            $0,         9           # $s2 = image_size = 9
    j       loop


loop:
    bgt     $s1,            $s2,        break       # If i == image_size, jump to break

    move    $s0,            $0                      # $s0 = 0, move back to the beginning of the array
    lw      $a0,            0($s0)                  # Load left_image[i] into $a0
    lw      $a1,            36($s0)                 # Load right_image[i] into $a1
    jal     abs_diff                                # Call abs_diff
    sw      $v0,            72($s0)                 # Store the result of abs_diff in sad_array[i]

    addi    $s1,            $s1,        1           # i++
    addi    $s0,            $s0,        4           # Move to the next element in the array
    j       loop                                    # repeat loop


break:
    # recursive_sum(sad_array, 9)
    move    $s0,            $0                      # $s0 = 0, move back to the beginning of the array
    lw      $a0,            72($s0)                 # Load the base address of sad_array into $a0
    addi    $a1,            $0,         9           # Load the size of the array into $a1
    jal     recursive_sum                           # call recursive_sum

    sw      $v0,            0($t2)                  # Store the returned value in $t2

    j       end


end:
    j       end                                     # Infinite loop at the end of the program.



    # Absolute difference between two integers
abs_diff:
    sub     $t1,            $a0,        $a1         # Subtract second integer from first integer
    sra     $t2,            $t1,        31          # Arithmetic right shift to get sign of the difference
    xor     $t1,            $t1,        $t2         # XOR to flip the sign if necessary
    sub     $v0,            $t1,        $t2         # Subtract the flipped sign from the difference to get absolute difference
    jr      $ra                                     # Return to the calling function



    # Registers:
    # $a0: pointer to the array A
    # $a1: n, the number of elements in the array
    # $t0: loop counter
    # $t1: temporary variable for storing the sum
    # $t2: pointer to the current element of the array

sum:
    # Initialize sum to 0
    li      $t1,            0                       # $t1 = 0

    # Loop through the array
    li      $t0,            0                       # $t0 = 0 (loop counter)
loop:
    # Check if we have reached the end of the array
    bge     $t0,            $a1,        loop_end    # If $t0 >= $a1, exit loop

    # Load the current element of the array into $t2
    mul     $t2,            $t0,        4           # $t2 = $t0 * 4 (each integer is 4 bytes)
    add     $t2,            $t2,        $a0         # $t2 = address of A[i]
    lw      $t2,            0($t2)                  # $t2 = A[i]

    add     $t1,            $t1,        $t2         # $t1 = $t1 + A[i]
    addi    $t0,            $t0,        1           # $t0++ = i++

    j       loop_start                              # Repeat the loop

    # End of loop
break:
    # Return the sum
    move    $v0,            $t1                     # Return value in $v0
    jr      $ra                                     # Return




    #     # Recursive sum of an array of integers
    # recursive_sum:
    #     addi    $sp,            $sp,        -8      # Adjust sp
    #     addi    $t0,            $a1,        -1      # Compute size - 1
    #     sw      $t0,            0($sp)              # Save size - 1 to stack
    #     sw      $ra,            4($sp)              # Save return address
    #     bne     $a1,            $zero,      else    # size == 0 ?
    #     addi    $v0,            $0,         0       # If size == 0, set return value to 0
    #     addi    $sp,            $sp,        8       # Adjust sp
    #     jr      $ra                                 # Return

    # else:
    #     add     $a1,            $t0,        $0      #update the second argument
    #     jal     recursive_sum
    #     lw      $t0,            0($sp)              # Restore size - 1 from stack
    #     sll     $t1,            $t0,        2       # Multiply size by 4
    #     add     $t1,            $t1,        $a0     # Compute & arr[ size - 1 ]
    #     lw      $t2,            0($t1)              # t2 = arr[ size - 1 ]
    #     add     $v0,            $v0,        $t2     # retval = $v0 + arr[size - 1]
    #     lw      $ra,            4($sp)              # restore return address from stack
    #     addi    $sp,            $sp,        8       # Adjust sp
    #     jr      $ra                                 # Return
