using NUnit.Framework;
using OOAP1;

namespace TwoWayList.Tests
{
    public class TwoWayListTests
    {
        [SetUp]
        public void Setup()
        {
        }

        [Test]
        public void Test_Constructor()
        {
            var list = new TwoWayList<int>();
            Assert.AreEqual(false, list.IsValue);
            Assert.AreEqual(false, list.IsTail);
            Assert.AreEqual(false, list.IsHead);
        }
        
        [Test]
        public void Test_MoveToHead_WhenListIsEmpty()
        {
            var list = new TwoWayList<int>();
            list.MoveToHead();
            Assert.AreEqual(OperationStatus.ERR, list.MoveToHeadStatus);
        }
        
        [Test]
        public void Test_MoveToHead_WhenListIsNotEmpty()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.MoveToHead();
            Assert.AreEqual(OperationStatus.OK, list.MoveToHeadStatus);
        }
        
        [Test]
        public void Test_Replace_WhenCursorIsNull()
        {
            var list = new TwoWayList<int>();
            list.Replace(1);
            Assert.AreEqual(OperationStatus.ERR, list.ReplaceStatus);
        }
        
        [Test]
        public void Test_Replace_WhenCursorIsNotNull()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.Replace(2);
            Assert.AreEqual(OperationStatus.OK, list.ReplaceStatus);
            Assert.AreEqual(2, list.Value());
        }
        
        [Test]
        public void Test_Find_WhenListIsEmpty()
        {
            var list = new TwoWayList<int>();
            list.Find(3);
            Assert.AreEqual(OperationStatus.OK, list.FindStatus);
        }
        
        [Test]
        public void Test_Find_WhenCursorNoElementAfter()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.AddTail(2);
            list.Find(3);
            Assert.AreEqual(OperationStatus.OK, list.FindStatus);
            Assert.AreEqual(list.Value(), 1);
        }
        
        [Test]
        public void Test_Find_WhenCursorElementAfterExists()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.AddTail(2);
            list.AddTail(2);
            list.AddTail(3);
            list.Find(2);
            Assert.AreEqual(OperationStatus.OK, list.FindStatus);
            Assert.AreEqual(list.Value(), 2);
        }
        
        [Test]
        public void Test_Find_WhenValueEqualsCurrent()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.AddTail(2);
            list.AddTail(2);
            list.AddTail(3);
            list.Find(1);
            Assert.AreEqual(OperationStatus.OK, list.FindStatus);
            Assert.AreEqual(list.Value(), 1);
        }

        [Test]
        public void Test_RemoveAll_WhenCursorNodeIsHeadAndRemoved()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.AddTail(2);
            list.AddTail(2);
            list.AddTail(3);
            list.RemoveAll(1);
            Assert.AreEqual(2, list.Value());
            Assert.AreEqual(3, list.Size());
        }
        
        [Test]
        public void Test_RemoveAll_WhenCursorNodeIsRemovedSeveralTimes()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.AddTail(2);
            list.AddTail(2);
            list.AddTail(3);
            list.RemoveAll(1);
            Assert.AreEqual(2, list.Value());
        }
        
        [Test]
        public void Test_RemoveAll_WhenSeveralNodesAreRemoved()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.AddTail(2);
            list.AddTail(3);
            list.AddTail(2);
            list.AddTail(4);
            list.MoveRight();
            list.MoveRight();
            list.RemoveAll(2);
            
            Assert.AreEqual(3, list.Value());
            Assert.AreEqual(3, list.Size());
        }
        
        [Test]
        public void Test_MoveToTail_WhenOneNode()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.MoveToTail();
            Assert.AreEqual(1, list.Value());
        }
        
        [Test]
        public void Test_MoveToTail_WhenEmptyList()
        {
            var list = new TwoWayList<int>();
            list.MoveToTail();
            
            Assert.AreEqual(OperationStatus.ERR, list.MoveToTailStatus);
        }
        
        [Test]
        public void Test_MoveRight_WhenNoNodesRighter()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.MoveRight();

            Assert.AreEqual(OperationStatus.ERR, list.MoveRightStatus);
        }
        
        [Test]
        public void Test_MoveRight_WhenSuccess()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.AddTail(2);
            list.MoveRight();

            Assert.AreEqual(OperationStatus.OK, list.MoveRightStatus);
            Assert.AreEqual(true, list.IsTail);
            Assert.AreEqual(2, list.Value());
        }
        
        [Test]
        public void Test_PutRight_WhenEmptyList()
        {
            var list = new TwoWayList<int>();
            list.PutRight(1);

            Assert.AreEqual(OperationStatus.ERR, list.PutRightStatus);
        }
        
        [Test]
        public void Test_PutRight_WhenNewNodeInTail()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.PutRight(2);

            Assert.AreEqual(OperationStatus.OK, list.PutRightStatus);
            Assert.AreEqual(1, list.Value());
            Assert.AreEqual(2, list.Size());
        }
        
        [Test]
        public void Test_PutRight_WhenNewNodeInMiddle()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.AddTail(3);
            list.PutRight(2);

            Assert.AreEqual(OperationStatus.OK, list.PutRightStatus);
            Assert.AreEqual(1, list.Value());
            Assert.AreEqual(3, list.Size());
        }
        
        [Test]
        public void Test_PutLeft_WhenEmptyList()
        {
            var list = new TwoWayList<int>();
            list.PutLeft(2);

            Assert.AreEqual(OperationStatus.ERR, list.PutLeftStatus);
        }
        
        [Test]
        public void Test_PutLeft_WhenNewNodeInHead()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.AddTail(3);
            list.PutLeft(2);

            Assert.AreEqual(OperationStatus.OK, list.PutLeftStatus);
            Assert.AreEqual(1, list.Value());
            Assert.AreEqual(3, list.Size());
        }
        
        [Test]
        public void Test_Remove_WhenListIsEmpty()
        {
            var list = new TwoWayList<int>();
            list.Remove();

            Assert.AreEqual(OperationStatus.ERR, list.RemoveStatus);
        }
        
        [Test]
        public void Test_Remove_WhenOneElement()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.Remove();

            Assert.AreEqual(OperationStatus.OK, list.RemoveStatus);
            Assert.AreEqual(0, list.Size());
            Assert.AreEqual(false, list.IsValue);
        }
        
        [Test]
        public void Test_Remove_WhenHead()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.AddTail(2);
            list.Remove();

            Assert.AreEqual(OperationStatus.OK, list.RemoveStatus);
            Assert.AreEqual(1, list.Size());
            Assert.AreEqual(true, list.IsValue);
        }
        
        [Test]
        public void Test_Remove_WhenListTail()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.AddTail(2);
            list.MoveRight();
            list.Remove();

            Assert.AreEqual(OperationStatus.OK, list.RemoveStatus);
            Assert.AreEqual(1, list.Size());
            Assert.AreEqual(true, list.IsValue);
        }
        
        [Test]
        public void Test_Remove_WhenInMiddle()
        {
            var list = new TwoWayList<int>();
            list.AddTail(1);
            list.AddTail(2);
            list.AddTail(3);
            list.MoveRight();
            list.Remove();

            Assert.AreEqual(OperationStatus.OK, list.RemoveStatus);
            Assert.AreEqual(2, list.Size());
        }
    }
}