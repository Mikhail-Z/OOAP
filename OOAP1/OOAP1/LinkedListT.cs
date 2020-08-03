//2.2 Вопрос: Почему операция tail не сводима к другим операциям (если исходить из эффективной реализации)?
//Ответ: Операция tail не сводима к другим операциям исходя из эффективной реализации,
//потому что эффективная реализация (O(1)) сама по себе является атомарной операцией (установка указателя (курсора) на другой объект)

//2.3 Вопрос: Операция поиска всех узлов с заданным значением, выдающая список таких узлов, уже не нужна. Почему?
//Ответ: Такая операция бессмыслена для реализации с использованием курсора, так как отсутствует операции с использованием переданных в виде аргументов узлов

using System;

namespace OOAP1
{
    public class Node<T> where T : IComparable
    {   
        public T value;
        public Node<T> next;
        public Node<T> prev;
        public Node(T _value) { value = _value; }
    }

    public enum OperationStatus
    {
        NIL,
        OK,
        ERR
    }

    public class LinkedList<T> where T : IComparable
    {
        public Node<T> head;
        public Node<T> tail;
        
        private Node<T> cursor;
        
        public OperationStatus MoveToHeadStatus { get; private set; }
        public OperationStatus MoveToTailStatus { get; private set; } 
        public OperationStatus MoveRightStatus { get; private set; }
        public OperationStatus MoveLeftStatus { get; private set; }
        public OperationStatus PutLeftStatus { get; private set; }
        public OperationStatus PutRightStatus { get; private set; }
        public OperationStatus ValueStatus { get; private set; }
        public OperationStatus RemoveStatus { get; private set; }
        public OperationStatus ReplaceStatus { get; private set; }
        public OperationStatus FindStatus { get; private set; }
        public OperationStatus RemoveAllStatus { get; private set; }
        public OperationStatus ClearStatus { get; private set; }
        public OperationStatus AddTailStatus { get; private set; }

        /// <summary>    
        /// постусловие: создан пустой список, курсор указывает на null
        /// </summary>
        public LinkedList()
        {
            Clear();
            ClearStatus = OperationStatus.NIL;
        }

        /// <summary>
        /// предусловие: список непустой
        /// постусловие: курсор указывает на начало списка
        /// </summary>
        public void MoveToHead()
        {
            if (this.head == null)
            {
                MoveToHeadStatus = OperationStatus.ERR;
            }
            else
            {
                cursor = this.head;
                MoveToHeadStatus = OperationStatus.OK;
            }
        }
        
        /// <summary>
        /// предусловие: список непустой
        /// постусловие: курсор указывает на хвост списка  
        /// </summary>
        public void MoveToTail()
        {
            if (this.head == null)
            {
                MoveToTailStatus = OperationStatus.ERR;
            }
            else
            {
                cursor = this.tail;
                MoveToTailStatus = OperationStatus.OK;
            }
        }
        
        /// <summary>
        /// предусловие: список непустой и справа есть элемент
        /// постусловие: курсор указывает на узел правее
        /// </summary>
        public void MoveRight()
        {
            if (IsValue == false || this.cursor.next == null)
            {
                MoveRightStatus = OperationStatus.ERR;
            }
            else
            {
                cursor = cursor.next;
                MoveRightStatus = OperationStatus.OK;
            }
        }

        /// <summary>
        /// предусловие: список непустой
        /// постусловие: значение узла, на который указывает курсор
        /// </summary>
        public T Value()
        {
            if (IsValue == false)
            {
                ValueStatus = OperationStatus.ERR;
                return default;
            }

            ValueStatus = OperationStatus.OK;
            return this.cursor.value;
        }

        /// <summary>
        /// предусловие: стек непустой
        /// постусловие: в список вставлен перед текущим узлом новый узел с заданным значением
        /// </summary>
        public void PutRight(T value)
        {
            if (IsValue == false)
            {
                PutRightStatus = OperationStatus.ERR;
            }
            else
            {
                if (IsTail)
                {
                    AddTail(value);
                }
                else
                {
                    AddInMiddle(cursor, value);
                }
                PutRightStatus = OperationStatus.OK;
            }
        }

        /// <summary>
        /// предусловие: список непустой
        /// постусловие: в список вставлен следом за текущим узлом новый узел с заданным значением
        /// </summary>
        public void PutLeft(T value)
        {
            if (IsValue == false)
            {
                PutLeftStatus = OperationStatus.ERR;
            }
            else
            {
                if (this.cursor == this.head)
                {
                    AddHead(value);
                }
                else
                {
                    AddInMiddle(cursor.prev, value);
                }
                PutLeftStatus = OperationStatus.OK;
            }
        }

        /// <summary>
        /// предусловие: список непустой
        /// постусловие: в списке удален текущий узел (курсор смещается к правому соседу, если он есть, в противном случае курсор смещается к левому соседу, если он есть)
        /// </summary>
        public void Remove()
        {
            if (IsValue == false)
            {
                RemoveStatus = OperationStatus.ERR;
                return;
            }
            if (IsHead)
            {
                if (RemoveHeadNode() == false)
                {
                    RemoveStatus = OperationStatus.ERR;
                    return;
                }
                RemoveStatus = OperationStatus.OK;
            }
            else if (IsTail)
            {
                if (RemoveTailNode() == false)
                {
                    RemoveStatus = OperationStatus.ERR;
                    return;
                }
                RemoveStatus = OperationStatus.OK;
            }
            else
            {
                if (RemoveMiddleNode() == false)
                {
                    RemoveStatus = OperationStatus.ERR;
                    return;
                }
                RemoveStatus = OperationStatus.OK;
            }
        }

        /// <summary>
        /// постусловие: пустой список, статусы всех команд установлены в NIL
        /// </summary>
        public void Clear()
        {
            this.head = null;
            this.tail = null;
            this.cursor = null;

            MoveToHeadStatus = OperationStatus.NIL;
            MoveToTailStatus = OperationStatus.NIL;
            MoveRightStatus = OperationStatus.NIL;
            MoveLeftStatus = OperationStatus.NIL;
            PutLeftStatus = OperationStatus.NIL;
            PutRightStatus = OperationStatus.NIL;
            ValueStatus = OperationStatus.NIL;
            RemoveStatus = OperationStatus.NIL;
            ReplaceStatus = OperationStatus.NIL;
            RemoveAllStatus = OperationStatus.NIL;
            ClearStatus = OperationStatus.OK;
            AddTailStatus = OperationStatus.OK;
        }

        /// <summary>
        /// постусловие: возвращает количество элементов в списке
        /// </summary>
        public int Size()
        {
            var currentNode = this.head;
            int size = 0;
            while (currentNode != null)
            {
                currentNode = currentNode.next;
                size++;
            }

            return size;
        }

        /// <summary>
        /// постусловие: добавлен элемент в конец списка
        /// </summary>
        public void AddTail(T value)
        {
            var newNode = new Node<T>(value);
            if (head == null)
            {
                head = newNode;
                head.next = null;
                head.prev = null;
                this.cursor = head;
            }
            else
            {
                tail.next = newNode;
                newNode.prev = tail;
            }

            tail = newNode;
            AddTailStatus = OperationStatus.OK;
        }

        /// <summary>
        /// предусловие: список непустой
        /// постусловие: текущее значение курсора заменено на заданное
        /// </summary>
        public void Replace(T newValue)
        {
            if (IsValue == false)
            {
                ReplaceStatus = OperationStatus.ERR;
            }
            else
            {
                this.cursor.value = newValue;
                ReplaceStatus = OperationStatus.OK;
            }
        }

        /// <summary>
        /// предусловие: список непустой и за курсором есть узел с указанным значением
        /// постусловие: курсор установлен на следующий узел с указанным значением (по отношению к текущему узлу)
        /// </summary>
        public void Find(T value)
        {
            if (IsValue == false)
            {
                FindStatus = OperationStatus.ERR;
                return;
            }
            
            var currentNode = this.cursor.next;
            while (currentNode != null)
            {
                if (currentNode.value.CompareTo(value) == 0)
                {
                    this.cursor = currentNode;
                    FindStatus = OperationStatus.OK;
                    return;
                }

                currentNode = currentNode.next;
            }

            FindStatus = OperationStatus.ERR;
        }
        
        /// <summary>
        /// постусловие: в списке уделены все узлы с заданным значением, если курсор указывал на уделенный узел, то теперь он указывает на узел по правилам для операции Remove
        /// </summary>
        public void RemoveAll(T value)
        {
            var newCursor = this.cursor;
            this.cursor = this.head;
            while (this.cursor != null)
            {
                if (this.cursor.value.CompareTo(value) == 0)
                {
                    if (newCursor == this.cursor)
                    {
                        Remove();
                        newCursor = this.cursor;
                    }
                    else
                    {
                        Remove();
                    }
                    
                    if (this.cursor == this.tail)
                    {
                        break;
                    }
                }

                this.cursor = this.cursor.next;
            }

            this.cursor = newCursor;
            RemoveAllStatus = OperationStatus.OK;
        }

        /// <summary>
        /// постусловие: возврашает, указывает ли курсор на начало списка (для пустого списка возвращается false)
        /// </summary>
        public bool IsHead
        {
            get
            {
                if (IsValue == false)
                {
                    return false;
                }

                return this.head == this.cursor;
            }
        }

        /// <summary>
        /// постусловие: возврашает, указывает ли курсор на конец списка (для пустого списка возвращается false)
        /// </summary>
        public bool IsTail
        {
            get
            {
                if (IsValue == false)
                {
                    return false;
                }

                return this.tail == this.cursor;
            }
        }

        /// <summary>
        /// постусловие: возврашает, указывает ли курсор на узел (пустой ли список)
        /// </summary>
        public bool IsValue => this.cursor != null;
        
        private void AddHead(T value)
        {
            var newNode = new Node<T>(value);
            if (head == null)
            {
                tail = newNode;
                tail.next = null;
                tail.prev = null;
            }
            else if (head != null)
            {
                head.prev = newNode;
                newNode.next = head;
                head = head.prev;
            }

            head = newNode;
        }
        
        private void AddInMiddle(Node<T> nodeBefore, T value)
        {
            var newNode = new Node<T>(value);
            if (nodeBefore == null || nodeBefore.next == null)
            {
                return;
            }

            var next = nodeBefore.next;
            next.prev = newNode;
            newNode.next = next;
            newNode.prev = nodeBefore;
            nodeBefore.next = newNode;
        }
        
        private bool RemoveHeadNode()
        {
            if (head == null)
            {
                return false;
            }

            if (head.next == null)
            {
                head = null;
                tail = null;
                this.cursor = null;
            }
            else
            {
                head.next.prev = null;
                head = head.next;
                this.cursor = head;
            }
            
            return true;
        }

        private bool RemoveTailNode()
        {
            if (tail == null)
            {
                return false;
            }

            if (tail.prev == null)
            {
                head = null;
                tail = null;
                this.cursor = null;
            }
            else
            {
                tail.prev.next = null;
                tail = tail.prev;
                this.cursor = tail;
            }

            return true;
        }
        
        private bool RemoveMiddleNode()
        {
            if (this.cursor.prev == null || this.cursor.next == null)
            {
                return false;
            }

            var prev = this.cursor.prev;
            var next = this.cursor.next;

            prev.next = next;
            next.prev = prev;
            this.cursor = this.cursor.next;
            
            return true;
        }
    }
}