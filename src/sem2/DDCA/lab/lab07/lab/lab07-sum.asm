    #       
    # Calculate sum from A to B.
    #       
    # Authors:
    #	Basil Feitknecht, Camil Schmidt
    #       
    # use only:
    # 	ADD, SUB, SLT, XOR, AND, OR, NOR, J, ADDI, BEQ.



.text
main:
    addi    $s0,    $0,     12  # A = 5
    addi    $s1,    $0,     10  # B = 32767
    addi    $s1,    $s1,    1   # B = B + 1
    addi    $t2,    $0,     0   # S = 0
    slt     $t1,    $s1,    $s0 # A < B
    beq     $t1,    $0,     end # If B < A, jump to loop
    j       loop                # Jump to loop

loop:
    beq     $s0,    $s1,    end # If A == B, jump to end
    add     $t2,    $t2,    $s0 # S = S + A
    addi    $s0,    $s0,    1   # A = A + 1
    j       loop                # jump to loop


end:
    j       end                 # Infinite loop at the end of the program.
