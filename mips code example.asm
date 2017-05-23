.data 
A: .word 7, 6, 5, 4, 3, 2, 1

.text 
.globl main 
main: 
	la $a0, A 
	li $t0, 0
	jal sumRecursive # call fib 
	
	mtc1 $a3, $f0
	add $a3, $v0, $zero 
	li $v0, 1 
	syscall 

	li $v0, 10 
	syscall 

sumRecursive:
	addi $sp, $sp, -8
	sw $ra, 4($sp)
	sw $a1, 0($sp)
	
	sub.s $f0, $f0, $f0		# f0 - return result
	
	slti $t3, $a1, 0
	beq $t3, 0, L1
	addi $sp, $sp, 8
	
	jr $ra
		
L1:
	addi $a1, $a1, -1
	jal sumRecursive
	
	lw $a1, 0($sp)
	lw $ra, 4($sp)
	sll $t4, $a1, 2
	add $t5, $a0, $t4	# t5 = &array[i]
	lwc1 $f1, 0($t5)
	
	beq $a1, 0, skip
	sub.s $f2, $f2, $f2
	mtc1 $a1, $f2
	cvt.s.w $f2, $f2
	div.s $f1, $f1, $f2
	
skip:	
	addi $sp, $sp, 8
	
	add.s $f0, $f0, $f1	# v0 += array[i]
	jr $ra